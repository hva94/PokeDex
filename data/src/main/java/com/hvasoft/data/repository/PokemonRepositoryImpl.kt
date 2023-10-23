package com.hvasoft.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.map
import com.hvasoft.data.common.Constants
import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.local.dao.PokemonDao
import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.data.mapper.PokemonMapperEntity
import com.hvasoft.data.paging.PokemonRemoteMediator
import com.hvasoft.data.remote.PokemonApi
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonDao: PokemonDao,
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonMapperEntity: PokemonMapperEntity
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedPokemons(): Flow<PagingData<Pokemon>> {
        val pokemonsSourceFactory: () -> PagingSource<Int, PokemonEntity> = { pokemonDao.getPokemons() }
        return Pager(
            config = PagingConfig(pageSize = Constants.PAGINATION_ITEMS_PER_PAGE),
            initialKey = null,
            remoteMediator = PokemonRemoteMediator(
                pokemonApi = pokemonApi,
                pokemonDatabase = pokemonDatabase
            ),
            pagingSourceFactory = pokemonsSourceFactory
        ).flow
            .map { page -> page.map { pokemonMapperEntity.map(it) } }
    }

}