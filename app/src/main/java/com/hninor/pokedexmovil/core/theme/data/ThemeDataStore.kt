package com.hninor.pokedexmovil.core.theme.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeDataStore(context: Context) {
    private val dataStore = context.dataStore

    val themeFlow: Flow<Boolean> = dataStore.data.map { preferences ->
        preferences[IS_DARK_MODE] ?: false
    }

    suspend fun saveTheme(isDark: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_DARK_MODE] = isDark
        }
    }

    companion object {
        private val Context.dataStore by preferencesDataStore("theme_prefs")
        private val IS_DARK_MODE = booleanPreferencesKey("dark_mode")
    }
}