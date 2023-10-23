package com.hvasoft.pokedex.presentation.ui.home

import androidx.paging.PagingData
import com.hvasoft.domain.model.Pokemon

sealed class HomeState {
    object Loading : HomeState()
    data class Success(val pagingData: PagingData<Pokemon>) : HomeState()
    data class Failure(val errorMessage: String? = null) : HomeState()
}