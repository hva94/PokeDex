package com.hvasoft.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemons")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val url: String,
    val isFavorite: Boolean,
    val imageUrl: String?,
    val height: Double?,
    val weight: Double?,
    val types: List<PokemonTypeEntity>
)