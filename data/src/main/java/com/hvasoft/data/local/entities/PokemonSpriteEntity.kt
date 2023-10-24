package com.hvasoft.data.local.entities

import com.squareup.moshi.Json

data class PokemonSpriteEntity(
    @Json(name = "front_default") val frontDefault: String
)