package com.hninor.pokedexmovil.core.theme.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hninor.pokedexmovil.core.theme.data.ThemeDataStore
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ThemeViewModel(private val themeDataStore: ThemeDataStore) : ViewModel() {

    val isDarkTheme = themeDataStore.themeFlow.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun toggleTheme() {
        viewModelScope.launch {
            themeDataStore.saveTheme(!isDarkTheme.value)
        }
    }
}