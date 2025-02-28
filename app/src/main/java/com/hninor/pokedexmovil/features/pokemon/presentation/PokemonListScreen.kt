package com.hninor.pokedexmovil.features.pokemon.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.hninor.pokedexmovil.R
import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonDetailResponse
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen() {
    val viewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)

    val pokemonState by viewModel.pokemonList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    Scaffold(topBar = { TopAppBar(title = { Text("Pokémon List") }) }) {
        when {
            pokemonState == null -> LoadingScreen()
            pokemonState!!.isSuccess -> PokemonList(
                pokemonState?.getOrNull()!!,
                isLoading,
                viewModel::loadMorePokemon,
                modifier = Modifier.padding(it)
            )

            pokemonState!!.isFailure -> ErrorScreen("Error: ${pokemonState!!.exceptionOrNull()?.message}") {
                viewModel.loadMorePokemon()
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    isLoading: Boolean,
    onLoadMorePokemon: () -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        items(pokemonList) { pokemon ->
            PokemonItem(pokemon)
        }

        item {
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { testTag = "loading-wheel" },
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = Color.Red
                    )
                }
            } else {
                LaunchedEffect(Unit) {
                    onLoadMorePokemon()
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = pokemon.name, fontWeight = FontWeight.Bold)
                Row {
                    pokemon.types.forEach { type ->
                        Text(
                            text = type,
                            modifier = Modifier
                                .padding(end = 4.dp)
                                .background(Color.LightGray)
                                .padding(4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    // Display a loading indicator
    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { testTag = "loading-wheel" },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color.Red
        )
    }
}

@Composable
fun ErrorScreen(message: String, retryAction: () -> Unit) {
    // Display an error message with a retry button

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, Modifier.padding(16.dp))
        Button(onClick = { retryAction() }) {
            Text(text = stringResource(id = R.string.intentar_nuevamente))
        }

    }

}

