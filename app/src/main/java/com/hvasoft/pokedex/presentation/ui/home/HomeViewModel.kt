package com.hvasoft.pokedex.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.hvasoft.domain.interactor.GetPagedPokemonsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
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
                getPagedPokemonsUseCase()
                    .flowOn(Dispatchers.IO)
                    .cachedIn(viewModelScope)
                    .collect { pagingData ->
                        _uiState.update { HomeState.Success(pagingData = pagingData) }
                    }
            } catch (e: Exception) {
                _uiState.update { HomeState.Failure(e.localizedMessage) }
            }
        }
    }
}