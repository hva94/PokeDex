package com.hvasoft.data.mapper

import com.hvasoft.data.local.entity.PokemonEntity
import com.hvasoft.domain.model.Pokemon

class PokemonMapper : Mapper<Pokemon, PokemonEntity> {

    override fun map(input: Pokemon): PokemonEntity {
        return with(input) {
            PokemonEntity(
                id = 0,
                name = name,
                url = url,
                isFavorite = false
            )
        }
    }

}