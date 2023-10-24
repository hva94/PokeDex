package com.hvasoft.data.mapper

import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.domain.model.Pokemon
import javax.inject.Inject

class PokemonEntityToModelMapper @Inject constructor(
    private val pokemonTypeEntityToModelMapper: PokemonTypeEntityToModelMapper,
    private val pokemonSpriteEntityToModelMapper: PokemonSpriteEntityToModelMapper
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
                types = types?.map { pokemonTypeEntity -> pokemonTypeEntityToModelMapper.map(pokemonTypeEntity) } ?: emptyList(),
                sprites = sprites?.map { pokemonSpriteEntity -> pokemonSpriteEntityToModelMapper.map(pokemonSpriteEntity) } ?: emptyList()
            )
        }
    }
}