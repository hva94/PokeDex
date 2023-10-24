package com.hvasoft.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false) val pokemonId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)