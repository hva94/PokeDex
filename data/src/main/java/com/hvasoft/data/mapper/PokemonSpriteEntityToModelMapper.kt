package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonSpriteEntity
import com.hvasoft.domain.model.PokemonSprite
import javax.inject.Inject

class PokemonSpriteEntityToModelMapper @Inject constructor() : Mapper<PokemonSpriteEntity, PokemonSprite> {
    override fun map(input: PokemonSpriteEntity): PokemonSprite {
        return with(input) {
            PokemonSprite(
                frontDefault = frontDefault
            )
        }
    }
}