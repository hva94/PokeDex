package com.hvasoft.domain.interactor

import android.database.sqlite.SQLiteConstraintException
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToggleFavoritePokemonUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : ToggleFavoritePokemonUseCase {
    override suspend fun invoke(pokemon: Pokemon): Pokemon = withContext(Dispatchers.IO) {
        try {
            val pokemonUpdated = pokemon.copy(isFavorite = !pokemon.isFavorite)
            val result = repository.updatePokemon(pokemonUpdated)
            if (result == 1)
                pokemonUpdated
            else
                throw SQLiteConstraintException()
        } catch (exception: SQLiteConstraintException) {
            throw exception
        }
    }
}