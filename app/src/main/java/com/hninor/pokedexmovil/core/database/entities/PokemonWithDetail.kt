package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonStat

data class PokemonWithDetails(
    @Embedded val pokemon: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val stats: List<PokemonStatEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val abilities: List<PokemonAbilityEntity>,
    @Relation(
        parentColumn = "id",
        entityColumn = "pokemonId"
    )
    val sprites: List<PokemonSpriteEntity>
)

fun PokemonWithDetails.toDomain(): Pokemon {
    return Pokemon(
        id = pokemon.id,
        name = pokemon.name,
        imageUrl = pokemon.imageUrl,
        types = pokemon.types.split(","), // Assuming types are stored as a comma-separated string
        sprites = sprites.map { it.spriteUrl },
        stats = stats.map { PokemonStat(it.name, it.value) },
        abilities = abilities.map { it.abilityName }
    )
}