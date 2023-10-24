package com.hvasoft.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val height: Int?,
    val weight: Int?,
    val types: List<PokemonType>,
    val sprites: List<PokemonSprite>,
    val isFavorite: Boolean
)