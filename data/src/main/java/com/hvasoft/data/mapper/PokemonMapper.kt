package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonTypeEntity
import com.hvasoft.domain.model.PokemonType

class PokemonMapper : Mapper<PokemonType, PokemonTypeEntity> {

    override fun map(input: PokemonType): PokemonTypeEntity {
        return with(input) {
            PokemonTypeEntity(
                id = id,
                name = name,
                url = url
            )
        }
    }

}