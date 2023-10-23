package com.hvasoft.data.remote.model

import com.google.gson.annotations.SerializedName

data class PokemonResponseDTO(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val results: List<PokemonDTO>
)
