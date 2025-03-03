package com.hninor.pokedexmovil.core.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.hninor.pokedexmovil.core.database.entities.PokemonAbilityEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonPageEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonSpriteEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonStatEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon: List<PokemonEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStats(stats: List<PokemonStatEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAbilities(abilities: List<PokemonAbilityEntity>)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSprites(sprites: List<PokemonSpriteEntity>)


    @Query("SELECT * FROM pokemon_table ORDER BY id LIMIT :limit OFFSET :offset")
    fun getPagedPokemon(limit: Int, offset: Int): Flow<List<PokemonEntity>>

    @Transaction
    @Query("SELECT * FROM pokemon_table WHERE id = :pokemonId")
    suspend fun getPokemonWithDetails(pokemonId: Int): PokemonWithDetails?


    @Query("DELETE FROM pokemon_table")
    suspend fun clearAllPokemon()


    @Query("DELETE FROM pokemon_stat_table WHERE pokemonId = :pokemonId")
    suspend fun deleteStats(pokemonId: Int)

    @Query("DELETE FROM pokemon_abilities_table WHERE pokemonId = :pokemonId")
    suspend fun deleteAbilities(pokemonId: Int)

    @Query("DELETE FROM pokemon_sprites_table WHERE pokemonId = :pokemonId")
    suspend fun deleteSprites(pokemonId: Int)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonPage(pokemonPage: PokemonPageEntity)

    @Query("SELECT hasNextPage FROM pokemon_page WHERE offsetPage = :offset LIMIT 1")
    suspend fun hasNextPage(offset: Int): Boolean?
}
