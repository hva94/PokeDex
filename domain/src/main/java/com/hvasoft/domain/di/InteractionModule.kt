package com.hvasoft.domain.di

import com.hvasoft.domain.interactor.GetPagedPokemonsUseCase
import com.hvasoft.domain.interactor.GetPagedPokemonsUseCaseImpl
import com.hvasoft.domain.interactor.UpdatePokemonUseCase
import com.hvasoft.domain.interactor.UpdatePokemonUseCaseImpl
import com.hvasoft.domain.repository.PokemonRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object InteractionModule {

    @Provides
    fun provideGetPagedPokemonsUseCase(
        pokemonRepository: PokemonRepository
    ): GetPagedPokemonsUseCase = GetPagedPokemonsUseCaseImpl(pokemonRepository)

    @Provides
    fun provideUpdatePokemonUseCase(
        pokemonRepository: PokemonRepository
    ): UpdatePokemonUseCase = UpdatePokemonUseCaseImpl(pokemonRepository)

}