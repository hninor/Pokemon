package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_abilities_table",
    foreignKeys = [ForeignKey(
        entity = PokemonEntity::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PokemonAbilityEntity(
    @PrimaryKey(autoGenerate = true) val abilityId: Int = 0,
    val pokemonId: Int,
    val abilityName: String
)