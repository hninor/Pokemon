package com.hninor.pokedexmovil.features.pokemon_detail.presentation

import android.os.Build.VERSION.SDK_INT
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.hninor.pokedexmovil.core.getTypeColor
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonStat
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokemonDetailScreen(
    pokemon: Pokemon,
    onBackClick: () -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(pokemon.name.capitalize(Locale.ROOT)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PokemonImagesSection(pokemon)


            PokemonTypesSection(pokemon.types)


            PokemonStatsSection(pokemon.stats)


            PokemonAbilitiesSection(pokemon.abilities)


        }
    }
}


@Composable
fun PokemonImagesSection(pokemon: Pokemon) {
    val context = LocalContext.current

    val gifEnabledLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Main Image
        AsyncImage(
            model = pokemon.imageUrl,
            contentDescription = "Main Pokémon Image",
            modifier = Modifier.size(180.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Alternate Sprites
        Row(horizontalArrangement = Arrangement.Center) {
            pokemon.sprites.forEach { spriteUrl ->
                AsyncImage(
                    model = spriteUrl,
                    imageLoader = gifEnabledLoader,
                    contentDescription = "Alternate Sprite",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(4.dp)

                )
            }
        }
    }
}


@Composable
fun PokemonTypesSection(types: List<String>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        types.forEach { type ->
            Box(
                modifier = Modifier
                    .background(getTypeColor(type), shape = RoundedCornerShape(8.dp))
                    .padding(8.dp)
            ) {
                Text(type, color = Color.White)
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Composable
fun PokemonStatsSection(stats: List<PokemonStat>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Stats", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        stats.forEach { stat ->


            val animatedProgress by animateFloatAsState(
                targetValue = stat.value / 100f,
                animationSpec = tween(durationMillis = 500), label = ""
            )

            val progressColor = when {
                stat.value >= 70 -> Color.Green
                stat.value >= 40 -> Color.Yellow
                else -> Color.Red
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stat.name, fontWeight = FontWeight.Bold)
                LinearProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .height(10.dp)
                        .clip(RoundedCornerShape(50)), // Smooth rounded edges
                    color = progressColor,
                    trackColor = Color.LightGray // Background for better contrast
                )
                Text(stat.value.toString())
            }
        }
    }
}


@Composable
fun PokemonAbilitiesSection(abilities: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Abilities", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))

        abilities.forEach { ability ->
            Text("• $ability", fontSize = 16.sp)
        }
    }
}
