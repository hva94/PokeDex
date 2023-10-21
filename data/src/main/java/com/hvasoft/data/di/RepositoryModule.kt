package com.hvasoft.data.di

import com.hvasoft.data.networking.PokemonApi
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
    ): PokemonRepository = PokemonRepositoryImpl(pokemonApi)

}