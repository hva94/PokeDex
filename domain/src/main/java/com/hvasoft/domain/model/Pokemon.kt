package com.hvasoft.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val url: String,
    val isFavorite: Boolean,
    val imageUrl: String?,
    val height: Double?,
    val weight: Double?,
    val types: List<PokemonType>
)