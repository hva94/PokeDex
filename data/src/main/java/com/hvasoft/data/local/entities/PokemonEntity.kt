package com.hvasoft.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String,
    val url: String,
    val isFavorite: Boolean,
    val height: Int?,
    val weight: Int?,
    val types: List<PokemonTypeEntity>? = null,
    val sprites: List<PokemonSpriteEntity>? = null
)