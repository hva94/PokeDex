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
) : GetPagedPokemonsUseCase {

    override fun invoke(): Flow<PagingData<Pokemon>> = flow {
        val pagingData: Flow<PagingData<Pokemon>> = pokemonRepository.getPagedPokemons()
            .map { pagingData ->
                pagingData.map { pokemonEntity ->
                    val newPokemonName = getNewPokemonName(
                        pokemonEntity.name,
                        pokemonEntity.sprites.first().frontDefault
                    )
                    Pokemon(
                        id = pokemonEntity.id,
                        name = newPokemonName,
                        url = pokemonEntity.url,
                        isFavorite = pokemonEntity.isFavorite,
                        height = pokemonEntity.height,
                        weight = pokemonEntity.weight,
                        types = pokemonEntity.types,
                        sprites = pokemonEntity.sprites
                    )
                }
            }
        emitAll(pagingData)
    }

    private fun getNewPokemonName(name: String, frontDefault: String): String {
        val words = name.split(" ")
        if (frontDefault.isNotEmpty()) {
            if (words.isNotEmpty()) {
                val firstWord = words.first()
                if (firstWord.length > 2)
                    return firstWord.replaceFirstChar { it.uppercaseChar() }
            }
            return name
        } else {
            if (words.size <= 2) {
                val firstLetters = words.take(2).map { it.first().uppercaseChar() }
                return firstLetters.joinToString("")
            }
            return name
        }
    }

}