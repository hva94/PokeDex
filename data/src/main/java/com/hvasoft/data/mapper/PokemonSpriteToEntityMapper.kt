package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonSpriteEntity
import com.hvasoft.domain.model.PokemonSprite
import javax.inject.Inject

class PokemonSpriteToEntityMapper @Inject constructor() : Mapper<PokemonSprite, PokemonSpriteEntity> {
    override fun map(input: PokemonSprite): PokemonSpriteEntity {
        return with(input) {
            PokemonSpriteEntity(
                frontDefault = frontDefault
            )
        }
    }
}