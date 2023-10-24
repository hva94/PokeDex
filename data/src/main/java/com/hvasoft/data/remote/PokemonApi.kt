package com.hvasoft.data.remote

import com.hvasoft.data.remote.model.PokemonDetailResponseDTO
import com.hvasoft.data.remote.model.PokemonResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {

    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Response<PokemonResponseDTO>

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetailById(
        @Path("id") id: Int
    ): Response<PokemonDetailResponseDTO>

}