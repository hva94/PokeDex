package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.domain.model.Pokemon
import javax.inject.Inject

class PokemonToEntityMapper @Inject constructor(
    private val pokemonTypeToEntityMapper: PokemonTypeToEntityMapper,
    private val pokemonSpriteToPokemonSpriteEntityMapper: PokemonSpriteToEntityMapper
): Mapper<Pokemon, PokemonEntity> {

    override fun map(input: Pokemon): PokemonEntity {
        return with(input) {
            PokemonEntity(
                id = id,
                name = name,
                url = url,
                isFavorite = isFavorite,
                height = height,
                weight = weight,
                types = types.map { pokemonType -> pokemonTypeToEntityMapper.map(pokemonType) },
                sprites = sprites.map { pokemonSprite -> pokemonSpriteToPokemonSpriteEntityMapper.map(pokemonSprite) }
            )
        }
    }

}