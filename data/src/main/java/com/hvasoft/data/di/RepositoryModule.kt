package com.hvasoft.data.di

import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.local.dao.PokemonDao
import com.hvasoft.data.mapper.PokemonEntityToModelMapper
import com.hvasoft.data.mapper.PokemonToEntityMapper
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
        pokemonDao: PokemonDao,
        pokemonApi: PokemonApi,
        pokemonDatabase: PokemonDatabase,
        pokemonToEntityMapper: PokemonToEntityMapper,
        pokemonEntityToModelMapper: PokemonEntityToModelMapper
    ): PokemonRepository = PokemonRepositoryImpl(
        pokemonDao,
        pokemonApi,
        pokemonDatabase,
        pokemonToEntityMapper,
        pokemonEntityToModelMapper
    )

}