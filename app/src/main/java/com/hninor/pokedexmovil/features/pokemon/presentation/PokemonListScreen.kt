package com.hninor.pokedexmovil.features.pokemon.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.hninor.pokedexmovil.R
import com.hninor.pokedexmovil.core.getTypeColor
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonListScreen(viewModel: PokemonListViewModel, onPokemonClick: (Pokemon) -> Unit) {

    val uiState by viewModel.uiState.collectAsState()
    val isPaginationLoading by viewModel.isPaginationLoading.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val filteredPokemonList by viewModel.filteredPokemonList.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()


    Scaffold(topBar = { TopAppBar(title = { Text("Pokémon List") }) }) {
        when (uiState) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> PokemonList(
                filteredPokemonList,
                isPaginationLoading,
                viewModel::loadMorePokemon,
                searchQuery,
                viewModel::onSearchQueryChanged,
                isRefreshing,
                viewModel::refreshPokemonList,
                onPokemonClick,
                modifier = Modifier.padding(it)
            )

            is UiState.Error -> ErrorScreen("Error: ${(uiState as UiState.Error).message}") {
                viewModel.loadMorePokemon()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonList(
    pokemonList: List<Pokemon>,
    isPaginationLoading: Boolean,
    onLoadMorePokemon: () -> Unit,
    searchQuery: String,
    onSearchQuery: (String) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    onPokemonClick: (Pokemon) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {

        SearchBar(query = searchQuery, onQueryChanged = onSearchQuery)

        Spacer(modifier = Modifier.padding(8.dp))

        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { if (searchQuery.isEmpty()) onRefresh() },
            modifier = Modifier.fillMaxWidth()
        ) {

            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Two columns
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(pokemonList, key = { pokemon -> pokemon.id }) { pokemon ->
                    PokemonItem(pokemon, onPokemonClick)
                }


                item(span = { GridItemSpan(2) }) {
                    if (searchQuery.isEmpty()) {
                        if (isPaginationLoading) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
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

    }

}


@Composable
fun SearchBar(query: String, onQueryChanged: (String) -> Unit) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
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
        trailingIcon = {
            if (query.isNotEmpty()) { // ✅ Show only when query is not empty
                IconButton(onClick = {
                    onQueryChanged("")
                    focusManager.clearFocus()
                    keyboardController?.hide()
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear Search")
                }
            }
        },
        singleLine = true
    )

}

@Composable
fun PokemonItem(pokemon: Pokemon, onPokemonClick: (Pokemon) -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onPokemonClick(pokemon) },
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                pokemon.types.forEach { type ->
                    Box(
                        modifier = Modifier
                            .background(getTypeColor(type), shape = RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            type,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
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

