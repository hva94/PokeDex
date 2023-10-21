package com.hvasoft.data.networking.model

import com.google.gson.annotations.SerializedName

data class PokemonMainResponse(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("next")
    val next: String? = null,
    @SerializedName("previous")
    val previous: String? = null,
    @SerializedName("results")
    val result: List<PokemonResponse>
)
