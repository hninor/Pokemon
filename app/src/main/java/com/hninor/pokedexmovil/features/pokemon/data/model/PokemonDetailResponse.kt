package com.hninor.pokedexmovil.features.pokemon.data.model

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>
)

data class Sprites(val front_default: String)

data class PokemonType(val type: TypeName)

data class TypeName(val name: String)
