package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon_page")
data class PokemonPageEntity(
    @PrimaryKey val offsetPage: Int,
    val hasNextPage: Boolean
)