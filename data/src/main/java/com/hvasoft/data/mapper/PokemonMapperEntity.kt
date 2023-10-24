package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.domain.model.Pokemon
import javax.inject.Inject

class PokemonMapperEntity @Inject constructor(
    private val pokemonTypeMapperEntity: PokemonTypeMapperEntity,
    private val pokemonSpriteMapperEntity: PokemonSpriteMapperEntity
): Mapper<PokemonEntity, Pokemon> {

    override fun map(input: PokemonEntity): Pokemon {
        return with(input) {
            Pokemon(
                id = id,
                name = name,
                url = url,
                isFavorite = isFavorite,
                height = height,
                weight = weight,
                types = types?.map { pokemonTypeEntity -> pokemonTypeMapperEntity.map(pokemonTypeEntity) } ?: emptyList(),
                sprites = sprites?.map { pokemonSpriteEntity -> pokemonSpriteMapperEntity.map(pokemonSpriteEntity) } ?: emptyList()
            )
        }
    }
}