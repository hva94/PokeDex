package com.hvasoft.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hvasoft.data.networking.PokemonApi
import com.hvasoft.data.paging.PokemonPagingSource
import com.hvasoft.data.util.Constants
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val pokemonApi: PokemonApi
) : PokemonRepository {

    override fun getPagedPokemons(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.POKEMON_ITEMS_PER_PAGE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PokemonPagingSource(
                    pokemonApi = pokemonApi,
                    limit = Constants.POKEMON_ITEMS_PER_PAGE,
                    offset = 0
                )
            }
        ).flow
    }

}