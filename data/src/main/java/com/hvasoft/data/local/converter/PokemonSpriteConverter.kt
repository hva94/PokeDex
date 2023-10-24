package com.hvasoft.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hvasoft.data.local.entities.PokemonSpriteEntity

class PokemonSpriteConverter {
    @TypeConverter
    fun fromPokemonSprite(value: List<PokemonSpriteEntity>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPokemonSprite(value: String?): List<PokemonSpriteEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonSpriteEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}