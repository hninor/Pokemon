package com.hninor.pokedexmovil.features.pokemon.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hninor.pokedexmovil.core.PokedexApplication
import com.hninor.pokedexmovil.features.pokemon.data.local.PokemonLocalDataSource
import com.hninor.pokedexmovil.features.pokemon.data.remote.ApiClient
import com.hninor.pokedexmovil.features.pokemon.data.remote.PokemonRemoteDataSource
import com.hninor.pokedexmovil.features.pokemon.data.repository.PokemonRepositoryImpl
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.use_cases.FilterPokemonUseCase
import com.hninor.pokedexmovil.features.pokemon.domain.use_cases.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


sealed class UiState {
    object Loading : UiState()
    data class Success(val pokemonList: List<Pokemon>) : UiState()
    data class Error(val message: String) : UiState()
}

class PokemonListViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val filterPokemonUseCase: FilterPokemonUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _isPaginationLoading = MutableStateFlow(false)
    val isPaginationLoading: StateFlow<Boolean> = _isPaginationLoading

    private val _hasMorePages = MutableStateFlow(true)
    val hasMorePages: StateFlow<Boolean> = _hasMorePages

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing

    private var _selectedPokemon = MutableStateFlow<Pokemon?>(null)
    val selectedPokemon: StateFlow<Pokemon?> = _selectedPokemon

    val filteredPokemonList: StateFlow<List<Pokemon>> =
        combine(_uiState, _searchQuery) { state, query ->
            if (state is UiState.Success) {
                filterPokemonUseCase(state.pokemonList, query)
            } else {
                emptyList()
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private var offset = 0
    private val limit = 20

    init {
        Log.d("PokemonListViewModel", "ViewModel Created: $this")
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (_isPaginationLoading.value || !_hasMorePages.value || _isRefreshing.value) return

        viewModelScope.launch {
            _isPaginationLoading.value = true
            getPokemonListUseCase(offset, limit)
                .collect { result ->
                    result.onSuccess { listResult ->
                        val currentList =
                            (_uiState.value as? UiState.Success)?.pokemonList.orEmpty()
                        _uiState.value = UiState.Success(currentList + listResult.pokemonList)
                        _hasMorePages.value = listResult.hasNextPage
                        offset += limit
                    }.onFailure { error ->
                        if (_uiState.value is UiState.Loading) {
                            _uiState.value =
                                UiState.Error(error.localizedMessage ?: "Unknown error")
                        }
                    }
                    _isPaginationLoading.value = false
                }
        }
    }

    fun refreshPokemonList() {
        if (isPaginationLoading.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            offset = 0
            getPokemonListUseCase(offset, limit).collect { result ->
                result.onSuccess { listResult ->
                    _uiState.value = UiState.Success(listResult.pokemonList)
                    _hasMorePages.value = listResult.hasNextPage
                    offset += limit
                }.onFailure {
                    //donothing
                }
                _isRefreshing.value = false
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun selectPokemon(pokemon: Pokemon) {
        _selectedPokemon.value = pokemon
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PokemonListViewModel(
                    GetPokemonListUseCase(
                        PokemonRepositoryImpl(
                            PokemonRemoteDataSource(ApiClient.create()),
                            PokemonLocalDataSource(PokedexApplication.database.pokemonDao())
                        )
                    ),
                    FilterPokemonUseCase()
                )
            }
        }
    }

}

