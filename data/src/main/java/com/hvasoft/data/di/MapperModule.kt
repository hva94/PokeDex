package com.hvasoft.data.di

import com.hvasoft.data.mapper.PokemonEntityToModelMapper
import com.hvasoft.data.mapper.PokemonSpriteEntityToModelMapper
import com.hvasoft.data.mapper.PokemonSpriteToEntityMapper
import com.hvasoft.data.mapper.PokemonToEntityMapper
import com.hvasoft.data.mapper.PokemonTypeEntityToModelMapper
import com.hvasoft.data.mapper.PokemonTypeToEntityMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Provides
    @Singleton
    fun providesPokemonSpriteToEntityMapper(): PokemonSpriteToEntityMapper = PokemonSpriteToEntityMapper()

    @Provides
    @Singleton
    fun providesPokemonTypeToEntityMapper(): PokemonTypeToEntityMapper = PokemonTypeToEntityMapper()

    @Provides
    @Singleton
    fun providesPokemonEntityToModelMapper(
        pokemonTypeEntityToModelMapper: PokemonTypeEntityToModelMapper,
        pokemonSpriteEntityToModelMapper: PokemonSpriteEntityToModelMapper
    ): PokemonEntityToModelMapper = PokemonEntityToModelMapper(
        pokemonTypeEntityToModelMapper,
        pokemonSpriteEntityToModelMapper
    )

    @Provides
    @Singleton
    fun providesPokemonSpriteEntityToModelMapper(): PokemonSpriteEntityToModelMapper = PokemonSpriteEntityToModelMapper()

    @Provides
    @Singleton
    fun providesPokemonToEntityMapper(
        pokemonTypeToEntityMapper: PokemonTypeToEntityMapper,
        pokemonSpriteToEntityMapper: PokemonSpriteToEntityMapper
    ): PokemonToEntityMapper = PokemonToEntityMapper(
        pokemonTypeToEntityMapper,
        pokemonSpriteToEntityMapper
    )

    @Provides
    @Singleton
    fun providesPokemonTypeEntityToModelMapper(): PokemonTypeEntityToModelMapper = PokemonTypeEntityToModelMapper()

}