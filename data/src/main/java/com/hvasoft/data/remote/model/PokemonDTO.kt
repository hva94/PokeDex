package com.hvasoft.data.remote.model

import com.squareup.moshi.Json

data class PokemonDTO(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)