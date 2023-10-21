package com.hvasoft.pokedex.presentation.ui.home

import androidx.paging.PagingData
import com.hvasoft.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class HomeState {
    object Loading : HomeState()
    object Empty : HomeState()
    data class Success(val pagingData: PagingData<Pokemon>) : HomeState()
    data class Failure(val errorMessage: String? = null) : HomeState()
}