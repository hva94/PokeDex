package com.hvasoft.domain.interactor

import androidx.paging.PagingData
import androidx.paging.map
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPagedPokemonsUseCaseImpl @Inject constructor(
    private val pokemonRepository: PokemonRepository
): GetPagedPokemonsUseCase {

    override fun invoke(): Flow<PagingData<Pokemon>> = flow {
        val pagingData = pokemonRepository.getPagedPokemons()
            .map { pagingData ->
                pagingData.map { pokemonEntity ->
                    val newPokemonName = getNewPokemonName(pokemonEntity.name)
                    Pokemon(
                        id = pokemonEntity.id,
                        name = newPokemonName,
                        url = pokemonEntity.url,
                        isFavorite = pokemonEntity.isFavorite
                    )
                }
            }
        emitAll(pagingData)
    }

    private fun getNewPokemonName(name: String): String {
        val words = name.split(" ")
        if (words.size <= 2) {
            val firstLetters = words.take(2).map { it.first().uppercaseChar() }
            return firstLetters.joinToString("")
        }
        return name
    }

}