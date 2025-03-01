package com.hninor.pokedexmovil.features.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>
)

data class Sprites(val front_default: String, val other: OtherSprites)

data class OtherSprites(@SerializedName("official-artwork") val officialArtwork: OfficialArtwork)

data class OfficialArtwork(val front_default: String)

data class PokemonType(val type: TypeName)

data class TypeName(val name: String)
