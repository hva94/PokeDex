package com.hvasoft.domain.interactor

import com.hvasoft.domain.model.Pokemon

interface UpdatePokemonUseCase {
    suspend operator fun invoke(pokemon: Pokemon)
}