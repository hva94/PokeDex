package com.hvasoft.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hvasoft.data.local.entities.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeysEntity>)

    @Query("SELECT * FROM remote_keys WHERE pokemonId = :pokemonId")
    suspend fun remoteKeysPokemonId(pokemonId: Int): RemoteKeysEntity

    @Query("DELETE FROM remote_keys")
    suspend fun clearRemoteKeys()
}