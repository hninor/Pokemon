@file:OptIn(ExperimentalMaterial3Api::class)

package com.hninor.pokedexmovil.features.pokemon.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen() {
    val viewModel: PokemonListViewModel =
        viewModel(factory = PokemonListViewModel.Factory)


    val uiState by viewModel.uiState.collectAsState()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Pokémon List") }) }) {
        when(uiState) {
            is UiState.Loading-> LoadingScreen()
            is UiState.Success -> PokemonList(
                (uiState as UiState.Success).pokemonList,
                isPaginationLoading,
                viewModel::loadMorePokemon,
                searchQuery,
                viewModel::onSearchQueryChanged,
                modifier = Modifier.padding(it)
            )

            is UiState.Error -> ErrorScreen("Error: ${(uiState as UiState.Error).message}") {
                viewModel.loadMorePokemon()
            }
        }
    }
}

@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    isPaginationLoading: Boolean,
    onLoadMorePokemon: () -> Unit,
    searchQuery: String,
    onSearchQuery: (String) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        SearchBar(query = searchQuery, onQueryChanged = onSearchQuery)
        
        Spacer(modifier = Modifier.padding(8.dp))
        LazyVerticalGrid(
            columns = GridCells.Fixed(2), // Two columns
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(pokemonList) { pokemon ->
                PokemonItem(pokemon)
            }

            item {
                if (isPaginationLoading) {
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

}


@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    TextField(
        value = query,
        onValueChange = onQueryChanged,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
        placeholder = { Text("Search Pokémon...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = "Search Icon")
        },
        singleLine = true
    )
}
@Composable
fun PokemonItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier
                 .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(80.dp)
            )

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

