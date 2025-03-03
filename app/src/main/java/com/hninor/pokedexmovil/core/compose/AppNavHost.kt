package com.hninor.pokedexmovil.core.compose

import SignInScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hninor.pokedexmovil.core.theme.presentation.ThemeViewModel
import com.hninor.pokedexmovil.features.login.presentation.SignInViewModel
import com.hninor.pokedexmovil.features.pokemon.presentation.PokemonListScreen
import com.hninor.pokedexmovil.features.pokemon.presentation.PokemonListViewModel
import com.hninor.pokedexmovil.features.pokemon_detail.presentation.PokemonDetailScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    themeViewModel: ThemeViewModel,
    signInViewModel: SignInViewModel
) {
    val viewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)

    val pokemon by viewModel.selectedPokemon.collectAsState()


    val startDestination = if (signInViewModel.userIsAlreadyLogged()) {
        "pokemon_list"
    } else {
        "login"
    }


    NavHost(navController, startDestination = startDestination) {

        composable("login") {
            SignInScreen(signInViewModel = signInViewModel) {
                navController.navigate("pokemon_list") {
                    popUpTo("login") { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
        composable("pokemon_list") {
            PokemonListScreen(viewModel, themeViewModel) {
                viewModel.selectPokemon(it)
                navController.navigate("pokemon_detail")
            }
        }
        composable(
            route = "pokemon_detail"
        ) {

            pokemon?.let {
                PokemonDetailScreen(it) {
                    navController.popBackStack()
                }
            }
        }
    }
}