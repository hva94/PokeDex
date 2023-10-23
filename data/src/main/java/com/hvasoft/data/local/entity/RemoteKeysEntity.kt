package com.hvasoft.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeysEntity(
    @PrimaryKey(autoGenerate = false)
    val pokemonId: Int = 0,
    val prevKey: Int?,
    val nextKey: Int?
)