package com.hvasoft.data.networking.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val imageUrl: String? = null
)
