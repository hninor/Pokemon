package com.hninor.pokedexmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.hninor.pokedexmovil.core.compose.AppNavHost
import com.hninor.pokedexmovil.features.pokemon.data.remote.ApiClient
import com.hninor.pokedexmovil.features.pokemon.data.remote.PokemonRemoteDataSource
import com.hninor.pokedexmovil.features.pokemon.data.repository.PokemonRepositoryImpl
import com.hninor.pokedexmovil.features.pokemon.presentation.PokemonListScreen
import com.hninor.pokedexmovil.core.theme.PokedexMovilTheme
import com.hninor.pokedexmovil.core.theme.data.ThemeDataStore
import com.hninor.pokedexmovil.core.theme.presentation.ThemeViewModel
import com.hninor.pokedexmovil.features.login.presentation.SignInViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val themeDataStore = ThemeDataStore(applicationContext)
        val themeViewModel = ThemeViewModel(themeDataStore)
        val signInViewModel = SignInViewModel(application)

        setContent {
            val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

            PokedexMovilTheme(darkTheme = isDarkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost(navController = rememberNavController(), themeViewModel, signInViewModel)
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PokedexMovilTheme {
        Greeting("Android")
    }
}