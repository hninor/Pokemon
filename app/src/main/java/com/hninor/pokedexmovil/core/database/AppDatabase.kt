package com.hninor.pokedexmovil.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hninor.pokedexmovil.core.database.dao.PokemonDao
import com.hninor.pokedexmovil.core.database.entities.PokemonAbilityEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonPageEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonSpriteEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonStatEntity

@Database(
    entities = [PokemonEntity::class, PokemonStatEntity::class, PokemonAbilityEntity::class, PokemonSpriteEntity::class, PokemonPageEntity::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}