package com.hvasoft.data.remote.model

import com.hvasoft.data.BuildConfig
import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.data.local.entities.PokemonSpriteEntity
import com.hvasoft.data.local.entities.PokemonTypeEntity
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
            types = getEntityTypes(types),
            sprites = getEntitySprites(sprites)
        )
    }

    private fun getEntityTypes(types: List<PokemonTypeDTO>?): List<PokemonTypeEntity> {
        if (types.isNullOrEmpty()) return emptyList()
        val pokemonTypeEntities = mutableListOf<PokemonTypeEntity>()
        types.forEach { pokemonTypeDTO ->
            pokemonTypeDTO.type.let { pokemonTypeEntities.add(it.toEntity()) }
        }
        return pokemonTypeEntities
    }

    private fun getEntitySprites(sprites: PokemonSpriteDTO?): List<PokemonSpriteEntity> =
        sprites?.let { listOf(it.toEntity()) } ?: emptyList()
}