package com.hvasoft.data.remote.model

import com.google.gson.annotations.SerializedName
import com.hvasoft.domain.model.Pokemon

data class PokemonDTO(
    @SerializedName("name") val name: String? = null,
    @SerializedName("url") val url: String? = null
) {
    fun toDomain(): Pokemon = Pokemon(
        id = url?.split("/")?.get(6)?.toInt() ?: 0,
        name = name ?: "",
        url = url ?: "",
        isFavorite = false
    )
}