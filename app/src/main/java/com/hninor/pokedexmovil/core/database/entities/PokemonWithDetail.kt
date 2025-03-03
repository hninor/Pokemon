package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Embedded
import androidx.room.Relation

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