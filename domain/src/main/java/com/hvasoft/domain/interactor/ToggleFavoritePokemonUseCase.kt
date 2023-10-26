package com.hvasoft.domain.interactor

import com.hvasoft.domain.model.Pokemon

interface ToggleFavoritePokemonUseCase {
    suspend operator fun invoke(pokemon: Pokemon): Pokemon
}