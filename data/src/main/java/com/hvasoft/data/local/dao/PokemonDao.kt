package com.hvasoft.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hvasoft.data.local.entity.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemons(tvShows: List<PokemonEntity>)

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): PagingSource<Int, PokemonEntity>

    @Query("SELECT COUNT(id) FROM pokemon")
    suspend fun existData(): Int

    @Query("UPDATE pokemon SET isFavorite = :isFavorite WHERE id = :pokemonId")
    suspend fun setIsFavorite(pokemonId: Int, isFavorite: Boolean)

    @Query("DELETE FROM pokemon")
    suspend fun clearPokemons()

    @Query("SELECT * FROM pokemon WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity

    @Query("SELECT * FROM pokemon WHERE isFavorite = 1")
    suspend fun getFavorites(): List<PokemonEntity>
}