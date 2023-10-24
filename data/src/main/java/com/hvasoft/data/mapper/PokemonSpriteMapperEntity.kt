package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonSpriteEntity
import com.hvasoft.domain.model.PokemonSprite

class PokemonSpriteMapperEntity : Mapper<PokemonSpriteEntity, PokemonSprite> {
    override fun map(input: PokemonSpriteEntity): PokemonSprite {
        return with(input) {
            PokemonSprite(
                frontDefault = frontDefault
            )
        }
    }
}