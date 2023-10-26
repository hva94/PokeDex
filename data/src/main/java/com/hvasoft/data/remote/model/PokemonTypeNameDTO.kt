package com.hvasoft.data.remote.model

import com.hvasoft.data.local.entities.PokemonTypeEntity
import com.squareup.moshi.Json

data class PokemonTypeNameDTO(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
) {
    fun toEntity(): PokemonTypeEntity {
        return PokemonTypeEntity(
            name = name,
            url = url
        )
    }
}