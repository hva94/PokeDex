package com.hvasoft.data.di

import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.mapper.PokemonMapper
import com.hvasoft.data.remote.PokemonApi
import com.hvasoft.data.repository.PokemonRepositoryImpl
import com.hvasoft.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun providePokemonRepository(
        pokemonApi: PokemonApi,
        pokemonDatabase: PokemonDatabase,
        pokemonMapper: PokemonMapper
    ): PokemonRepository = PokemonRepositoryImpl(
        pokemonApi,
        pokemonDatabase,
        pokemonMapper
    )

}