package com.hvasoft.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hvasoft.data.local.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPokemons(pokemons: List<PokemonEntity>)

    @Transaction
    @Query("SELECT * FROM pokemons")
    fun getPokemons(): PagingSource<Int, PokemonEntity>

    @Query("SELECT COUNT(id) FROM pokemons")
    suspend fun existData(): Int

    @Update
    suspend fun updatePokemon(pokemonEntity: PokemonEntity): Int

    @Query("DELETE FROM pokemons")
    suspend fun clearPokemons()

    @Query("SELECT * FROM pokemons WHERE id = :pokemonId")
    suspend fun getPokemonById(pokemonId: Int): PokemonEntity

}