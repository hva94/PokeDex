package com.hvasoft.data.remote.model

import com.hvasoft.data.BuildConfig
import com.hvasoft.data.local.entities.PokemonEntity
import com.squareup.moshi.Json

data class PokemonDetailResponseDTO(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "sprites") val sprites: PokemonSpriteDTO? = null,
    @Json(name = "height") val height: Int? = null,
    @Json(name = "weight") val weight: Int? = null,
    @Json(name = "types") val types: List<PokemonTypeDTO>? = null
) {
    fun toEntity(): PokemonEntity {
        return PokemonEntity(
            id = id,
            name = name,
            url = "${BuildConfig.BASE_URL}pokemon/$id/",
            isFavorite = false,
            height = height,
            weight = weight,
            types = null,
            sprites = sprites?.let { listOf(it.toEntity()) } ?: emptyList()
        )
    }
}