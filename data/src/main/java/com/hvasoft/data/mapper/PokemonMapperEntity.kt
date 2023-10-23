package com.hvasoft.data.mapper

import com.hvasoft.data.local.entity.PokemonEntity
import com.hvasoft.domain.model.Pokemon

class PokemonMapperEntity : Mapper<PokemonEntity, Pokemon> {

    override fun map(input: PokemonEntity): Pokemon {
        return with(input) {
            Pokemon(
                id = id,
                name = name,
                url = url,
                isFavorite = isFavorite
            )
        }
    }

}