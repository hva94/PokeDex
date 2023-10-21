package com.hvasoft.data.mapper

import com.hvasoft.data.networking.model.PokemonResponse
import com.hvasoft.domain.model.Pokemon

fun PokemonResponse.toDomain() = Pokemon(
    name = name,
    imageUrl = imageUrl
)