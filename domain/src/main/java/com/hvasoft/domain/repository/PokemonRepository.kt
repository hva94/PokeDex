package com.hvasoft.domain.repository

import androidx.paging.PagingData
import com.hvasoft.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonRepository {

    fun getPagedPokemons(): Flow<PagingData<Pokemon>>

}