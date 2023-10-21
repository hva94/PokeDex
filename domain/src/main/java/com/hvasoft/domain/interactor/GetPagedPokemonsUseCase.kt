package com.hvasoft.domain.interactor

import androidx.paging.PagingData
import com.hvasoft.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface GetPagedPokemonsUseCase {

    operator fun invoke(): Flow<PagingData<Pokemon>>

}