package com.hvasoft.domain.interactor

import androidx.paging.PagingData
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPagedPokemonsUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
): GetPagedPokemonsUseCase {

    override fun invoke(): Flow<PagingData<Pokemon>> = flow {
        emitAll(pokemonRepository.getPagedPokemons())
    }
}