package com.hninor.pokedexmovil.features.pokemon.data.model


data class PokemonListResponse(
    val results: List<PokemonBasic>,
    val next: String?
)

data class PokemonBasic(
    val name: String,
    val url: String
)