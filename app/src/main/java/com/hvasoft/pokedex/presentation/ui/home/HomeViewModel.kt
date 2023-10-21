package com.hvasoft.pokedex.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hvasoft.domain.interactor.GetPagedPokemonsUseCase
import com.hvasoft.domain.model.Pokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPagedPokemonsUseCase: GetPagedPokemonsUseCase
) : ViewModel() {

    private var _uiState = MutableStateFlow<HomeState>(HomeState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        fetchPokemons()
    }

    fun fetchPokemons() {
        _uiState.value = HomeState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getPagedPokemonsUseCase.invoke()
                    .flowOn(Dispatchers.IO)
                    .cachedIn(viewModelScope)
                    .map { page -> page.map { pokemon -> pokemon } }
                    .collect { pagingData ->
                        _uiState.value = HomeState.Success(pagingData = pagingData)
                    }
            } catch (e: Exception) {
                _uiState.value = HomeState.Failure(e.localizedMessage)
            }
        }
    }
}