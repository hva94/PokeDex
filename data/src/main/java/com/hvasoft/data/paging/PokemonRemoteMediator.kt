package com.hvasoft.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.hvasoft.data.common.Constants
import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.local.entity.RemoteKeysEntity
import com.hvasoft.data.mapper.PokemonMapper
import com.hvasoft.data.remote.PokemonApi
import com.hvasoft.data.remote.model.PokemonResponseDTO
import com.hvasoft.data.remote.network.getSuccess
import com.hvasoft.data.remote.network.makeSafeRequest
import com.hvasoft.domain.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonMapper: PokemonMapper
) : RemoteMediator<Int, Pokemon>() {

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
        state: PagingState<Int, Pokemon>
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
            val request = handleRequest { pokemonApi.getPokemons(
                limit = state.config.pageSize,
                offset = page
            ) }
            val data = withContext(Dispatchers.IO) {
                makeSafeRequest { request }.getSuccess()
            }
            val pokemonsData = mutableListOf<Pokemon>()
            data?.let {
                val pokemons = it.results.map { pokemonDTO -> pokemonDTO.toDomain() }
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
                val pokemon = pokemonsData.map { pokemon -> pokemonMapper.map(pokemon) }
                val keys = pokemon.map {
                    RemoteKeysEntity(
                        pokemonId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                pokemonDao.addPokemons(pokemon)
                remoteKeysDao.insertAll(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun handleRequest(request: suspend () -> Response<PokemonResponseDTO>): Response<PokemonResponseDTO> {
        return withContext(Dispatchers.IO) {
            request()
        }
    }

    private suspend fun getRemoteKeysForFirstItem(state: PagingState<Int, Pokemon>): RemoteKeysEntity? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tvShow ->
                remoteKeysDao.remoteKeysPokemonId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeysForLastItem(state: PagingState<Int, Pokemon>): RemoteKeysEntity? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tvShow ->
                remoteKeysDao.remoteKeysPokemonId(tvShow.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Pokemon>
    ): RemoteKeysEntity? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.let { pokemon ->
                remoteKeysDao.remoteKeysPokemonId(pokemon.id)
            }
        }
    }
}