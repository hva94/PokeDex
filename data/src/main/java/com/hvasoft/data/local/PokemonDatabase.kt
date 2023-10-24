package com.hvasoft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hvasoft.data.local.converter.PokemonSpriteConverter
import com.hvasoft.data.local.converter.PokemonTypeConverter
import com.hvasoft.data.local.dao.PokemonDao
import com.hvasoft.data.local.dao.RemoteKeysDao
import com.hvasoft.data.local.entities.PokemonEntity
import com.hvasoft.data.local.entities.RemoteKeysEntity

@Database(
    entities = [
        PokemonEntity::class,
        RemoteKeysEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonTypeConverter::class, PokemonSpriteConverter::class)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "pokeDex.db"
    }
}