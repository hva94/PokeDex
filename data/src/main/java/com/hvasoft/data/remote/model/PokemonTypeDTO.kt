package com.hvasoft.data.remote.model

import com.squareup.moshi.Json

data class PokemonTypeDTO(
    @Json(name = "type") val type: PokemonTypeNameDTO,
    @Json(name = "slot") val slot: Int
)