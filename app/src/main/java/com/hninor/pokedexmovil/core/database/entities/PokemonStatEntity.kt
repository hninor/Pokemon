package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_stat_table",
    foreignKeys = [ForeignKey(
        entity = PokemonEntity::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PokemonStatEntity(
    @PrimaryKey(autoGenerate = true) val statId: Int = 0,
    val pokemonId: Int,
    val name: String,
    val value: Int
)