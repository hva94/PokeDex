package com.hvasoft.data.remote.model

import com.hvasoft.data.local.entities.PokemonSpriteEntity
import com.squareup.moshi.Json

data class PokemonSpriteDTO(
    @Json(name = "front_default") val frontDefault: String
) {
     fun toEntity(): PokemonSpriteEntity {
          return PokemonSpriteEntity(
                frontDefault = frontDefault
          )
     }
}