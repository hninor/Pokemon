package com.hninor.pokedexmovil.features.pokemon.data.model

import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon


data class PokemonListResponse(
    val results: List<PokemonBasic>,
    val next: String?
)

data class PokemonBasic(
    val name: String,
    val url: String
)

data class PokemonListResultDTO(
    val pokemonList: List<PokemonDetailResponse>,
    val hasNextPage: Boolean
)