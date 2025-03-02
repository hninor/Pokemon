package com.hninor.pokedexmovil.features.pokemon.data.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    val id: Int,
    val name: String,
    val sprites: Sprites,
    val types: List<PokemonType>,
    val stats: List<StatPokemon>,
    val abilities: List<AbilityPokemon>
)

data class Sprites(
    val front_default: String,
    val back_default: String,
    val other: OtherSprites,
)

data class OtherSprites(@SerializedName("official-artwork") val officialArtwork: OfficialArtwork, val showdown: ShowDown)
data class ShowDown(val front_default: String, val back_default: String)

data class OfficialArtwork(val front_default: String)

data class PokemonType(val type: TypeName)

data class TypeName(val name: String)

data class StatPokemon(val base_stat: Int, val stat: Stat)

data class Stat(val name: String)

data class AbilityPokemon(val ability: Ability)

data class Ability(val name: String)
