package com.hvasoft.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hvasoft.data.local.dao.PokemonDao
import com.hvasoft.data.local.dao.RemoteKeysDao
import com.hvasoft.data.local.entity.PokemonEntity
import com.hvasoft.data.local.entity.RemoteKeysEntity

@Database(
    entities = [PokemonEntity::class, RemoteKeysEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): PokemonDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DB_NAME = "pokemon.db"
    }
}