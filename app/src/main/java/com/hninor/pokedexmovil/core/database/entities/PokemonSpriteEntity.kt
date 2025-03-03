package com.hninor.pokedexmovil.core.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "pokemon_sprites_table",
    foreignKeys = [ForeignKey(
        entity = PokemonEntity::class,
        parentColumns = ["id"],
        childColumns = ["pokemonId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class PokemonSpriteEntity(
    @PrimaryKey(autoGenerate = true) val spriteId: Int = 0,
    val pokemonId: Int,
    val spriteUrl: String
)