package com.hvasoft.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hvasoft.data.BuildConfig
import com.hvasoft.data.common.Constants
import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.data.local.entities.RemoteKeysEntity
import com.hvasoft.data.remote.PokemonApi
import com.hvasoft.data.remote.model.PokemonDTO
import com.hvasoft.data.remote.network.getSuccess
import com.hvasoft.data.remote.network.makeSafeRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase
) : RemoteMediator<Int, PokemonEntity>() {

    private val pokemonDao = pokemonDatabase.pokemonDao()
    private val remoteKeysDao = pokemonDatabase.remoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        val existData = withContext(Dispatchers.IO) {
            pokemonDao.existData()
        }
        return if (existData > 0) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PokemonEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: Constants.POKEMON_STARTING_PAGE_INDEX
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeysForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeysForLastItem(state)
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.nextKey
                    ?: Constants.POKEMON_STARTING_PAGE_INDEX
            }
        }

        try {
            val request = handleRequest {
                pokemonApi.getPokemons(
                    limit = state.config.pageSize,
                    offset = page
                )
            }
            val data = withContext(Dispatchers.IO) {
                makeSafeRequest { request }.getSuccess()
            }
            val pokemonsData = mutableListOf<PokemonEntity>()
            data?.let {
                val pokemons = it.results.map { pokemonDTO ->
                    val pokemonDetailDTO = getPokemonDetail(pokemonDTO)
                    PokemonEntity(
                        id = pokemonDTO.url.split("/")[6].toInt(),
                        name = pokemonDTO.name,
                        url = "${BuildConfig.BASE_URL}${pokemonDTO.url}",
                        isFavorite = false,
                        height = pokemonDetailDTO.height,
                        weight = pokemonDetailDTO.weight,
                        types = pokemonDetailDTO.types,
                        sprites = pokemonDetailDTO.sprites
                    )
                }
                pokemonsData.clear()
                pokemonsData.addAll(pokemons)
            }
            val endOfPagination = data?.count == page

            pokemonDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    remoteKeysDao.clearRemoteKeys()
                    pokemonDao.clearPokemons()
                }
                val prevKey =
                    if (page == Constants.POKEMON_STARTING_PAGE_INDEX) null else page.minus(1)
                val nextKey = if (endOfPagination) null else page.plus(1)
                val keys = pokemonsData.map {
                    RemoteKeysEntity(
                        pokemonId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                pokemonDao.addPokemons(pokemonsData)
                remoteKeysDao.insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getPokemonDetail(pokemonDTO: PokemonDTO): PokemonEntity {
        try {
            val request = handleRequest {
                pokemonApi.getPokemonDetailById(
                    id = pokemonDTO.url.split("/")[6].toInt()
                )
            }
            val data = withContext(Dispatchers.IO) {
                makeSafeRequest { request }.getSuccess()
            }
            var pokemonData: PokemonEntity? = null
            data?.let { pokemonDetailResponseDTO ->
                pokemonData =
                    PokemonEntity(
                        id = pokemonDetailResponseDTO.id,
                        name = pokemonDetailResponseDTO.name,
                        url = "",
                        isFavorite = false,
                        height = pokemonDetailResponseDTO.height,
                        weight = pokemonDetailResponseDTO.weight,
                        types = pokemonDetailResponseDTO.toEntity().types,
                        sprites = pokemonDetailResponseDTO.toEntity().sprites
                    )
            }
            return pokemonData!!
        } catch (e: IOException) {
            throw e
        } catch (e: HttpException) {
            throw e
        }
    }

    private suspend inline fun <reified T> handleRequest(crossinline request: suspend () -> Response<T>): Response<T> {
        return withContext(Dispatchers.IO) {
            request()
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { pokemon ->
                remoteKeysDao.remoteKeysPokemonId(pokemon.id)
            }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, PokemonEntity>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { pokemon ->
                remoteKeysDao.remoteKeysPokemonId(pokemon.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, PokemonEntity>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { pokemonEntity ->
                remoteKeysDao.remoteKeysPokemonId(pokemonEntity.id)
            }
        }
    }
}