package com.hvasoft.data.remote.model

import com.squareup.moshi.Json

data class PokemonResponseDTO(
    @Json(name = "count")
    val count: Int? = null,
    @Json(name = "next")
    val next: String? = null,
    @Json(name = "previous")
    val previous: String? = null,
    @Json(name = "results")
    val results: List<PokemonDTO>
)
