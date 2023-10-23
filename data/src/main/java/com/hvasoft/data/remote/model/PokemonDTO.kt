package com.hvasoft.data.remote.model

import com.google.gson.annotations.SerializedName
import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.data.local.entities.PokemonTypeEntity

data class PokemonDTO(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
    @SerializedName("imageUrl") val imageUrl: String?,
    @SerializedName("height") val height: Double?,
    @SerializedName("weight") val weight: Double?,
    @SerializedName("types") val types: List<PokemonTypeEntity>?
) {
    fun toEntity(): PokemonEntity = PokemonEntity(
        id = url.split("/")[6].toInt(),
        name = name,
        url = url,
        isFavorite = false,
        imageUrl = "",
        height = 0.0,
        weight = 0.0,
        types = types ?: emptyList()
    )
}