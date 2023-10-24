package com.hvasoft.domain.interactor

import android.database.sqlite.SQLiteConstraintException
import com.hvasoft.domain.model.Pokemon
import com.hvasoft.domain.repository.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdatePokemonUseCaseImpl @Inject constructor(
    private val repository: PokemonRepository
) : UpdatePokemonUseCase {
    override suspend fun invoke(pokemon: Pokemon) = withContext(Dispatchers.IO) {
        try {
            val pokemonUpdated = pokemon.copy(isFavorite = !pokemon.isFavorite)
            val result = repository.updatePokemon(pokemonUpdated)
            if (result == 0) throw Exception("Update error")
        } catch (e: SQLiteConstraintException){
            throw Exception("Update error")
        }
    }
}