package com.hvasoft.data.di

import com.hvasoft.data.mapper.PokemonMapper
import com.hvasoft.data.mapper.PokemonMapperEntity
import com.hvasoft.data.mapper.PokemonTypeMapperEntity
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
    fun providesPokemonTypeMapperEntity(): PokemonTypeMapperEntity = PokemonTypeMapperEntity()

    @Provides
    @Singleton
    fun providesPokemonMapper(): PokemonMapper = PokemonMapper()

    @Provides
    @Singleton
    fun providesPokemonMapperEntity(
        pokemonTypeMapperEntity: PokemonTypeMapperEntity
    ): PokemonMapperEntity = PokemonMapperEntity(pokemonTypeMapperEntity)

}