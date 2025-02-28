package com.hninor.pokedexmovil.features.pokemon.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonDetailResponse
import com.hninor.pokedexmovil.features.pokemon.data.remote.ApiClient
import com.hninor.pokedexmovil.features.pokemon.data.remote.PokemonRemoteDataSource
import com.hninor.pokedexmovil.features.pokemon.data.repository.PokemonRepositoryImpl
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import com.hninor.pokedexmovil.features.pokemon.domain.use_cases.GetPokemonListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonListViewModel(private val getPokemonListUseCase: GetPokemonListUseCase) : ViewModel() {
    private val _pokemonList = MutableStateFlow<Result<List<Pokemon>>?>(null)
    val pokemonList: StateFlow<Result<List<Pokemon>>?> = _pokemonList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _hasMorePages = MutableStateFlow(true) // Tracks if more pages exist
    val hasMorePages: StateFlow<Boolean> = _hasMorePages

    private var offset = 0
    private val limit = 20

    init {
        loadMorePokemon()
    }

    fun loadMorePokemon() {
        if (_isLoading.value || !_hasMorePages.value) return

        viewModelScope.launch {
            _isLoading.value = true
            getPokemonListUseCase(offset, limit)
                .collect { result ->
                    result.onSuccess { listResult ->
                        val currentList = _pokemonList.value?.getOrNull().orEmpty()
                        _pokemonList.value = Result.success(currentList + listResult.pokemonList)
                        _hasMorePages.value = listResult.hasNextPage
                        offset += limit
                    }.onFailure { error ->
                        _pokemonList.value = Result.failure(error)
                    }
                    _isLoading.value = false
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                PokemonListViewModel(
                    GetPokemonListUseCase(PokemonRepositoryImpl(PokemonRemoteDataSource(ApiClient.create())))
                )
            }
        }
    }

}

