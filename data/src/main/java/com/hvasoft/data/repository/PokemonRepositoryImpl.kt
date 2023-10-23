package com.hvasoft.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hvasoft.data.common.Constants
import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.mapper.PokemonMapper
import com.hvasoft.data.paging.PokemonPagingSource
import com.hvasoft.data.paging.PokemonRemoteMediator
import com.hvasoft.data.remote.PokemonApi
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonMapper: PokemonMapper
) : PokemonRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedPokemons(): Flow<PagingData<Pokemon>> {

       val pokemonSourceFactory = { PokemonPagingSource(pokemonApi, Constants.PAGINATION_ITEMS_PER_PAGE, 0) }

        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGINATION_ITEMS_PER_PAGE
            ),
            null,
            PokemonRemoteMediator(
                pokemonApi = pokemonApi,
                pokemonDatabase = pokemonDatabase,
                pokemonMapper = pokemonMapper
            ),
            pokemonSourceFactory
        ).flow
    }

}