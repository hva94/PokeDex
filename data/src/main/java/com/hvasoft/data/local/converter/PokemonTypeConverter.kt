package com.hvasoft.data.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hvasoft.data.local.entities.PokemonTypeEntity

class PokemonTypeConverter {

    @TypeConverter
    fun fromPokemonTypeList(value: List<PokemonTypeEntity>?): String? {
        val gson = Gson()
        return gson.toJson(value)
    }

    @TypeConverter
    fun toPokemonTypeList(value: String?): List<PokemonTypeEntity>? {
        val gson = Gson()
        val type = object : TypeToken<List<PokemonTypeEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}