package com.hvasoft.data.di

import android.content.Context
import androidx.room.Room
import com.hvasoft.data.local.PokemonDatabase
import com.hvasoft.data.local.dao.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesTvShowDatabase(@ApplicationContext context: Context): PokemonDatabase =
        Room.databaseBuilder(
            context,
            PokemonDatabase::class.java,
            PokemonDatabase.DB_NAME
        ).build()

    @Provides
    @Singleton
    fun providesTvShowDao(pokemonDatabase: PokemonDatabase): PokemonDao =
        pokemonDatabase.pokemonDao()

}