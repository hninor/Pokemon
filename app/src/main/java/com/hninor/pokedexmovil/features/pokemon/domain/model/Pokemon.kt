package com.hninor.pokedexmovil.features.pokemon.domain.model

data class Pokemon(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String>,
    val sprites: List<String>,
    val stats: List<PokemonStat>,
    val abilities: List<String>,
)


data class PokemonListResult(
    val pokemonList: List<Pokemon>,
    val hasNextPage: Boolean
)

data class PokemonStat(
    val name: String,
    val value: Int
)