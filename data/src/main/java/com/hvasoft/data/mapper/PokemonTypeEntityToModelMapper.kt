package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonTypeEntity
import com.hvasoft.domain.model.PokemonType
import javax.inject.Inject

class PokemonTypeEntityToModelMapper @Inject constructor(): Mapper<PokemonTypeEntity, PokemonType> {

    override fun map(input: PokemonTypeEntity): PokemonType {
        return with(input) {
            PokemonType(
                id = id,
                name = name,
                url = url
            )
        }
    }

}