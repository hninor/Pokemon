package com.hninor.pokedexmovil.core.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hninor.pokedexmovil.features.pokemon.presentation.PokemonListScreen
import com.hninor.pokedexmovil.features.pokemon.presentation.PokemonListViewModel
import com.hninor.pokedexmovil.features.pokemon_detail.presentation.PokemonDetailScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val viewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)

    val pokemon by viewModel.selectedPokemon.collectAsState()

    NavHost(navController, startDestination = "pokemon_list") {
        composable("pokemon_list") {
            PokemonListScreen(viewModel) {
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