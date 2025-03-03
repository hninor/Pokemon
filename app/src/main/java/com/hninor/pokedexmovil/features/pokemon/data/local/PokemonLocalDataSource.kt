package com.hninor.pokedexmovil.features.pokemon.data.local

import com.hninor.pokedexmovil.core.database.dao.PokemonDao
import com.hninor.pokedexmovil.core.database.entities.PokemonAbilityEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonPageEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonSpriteEntity
import com.hninor.pokedexmovil.core.database.entities.PokemonStatEntity
import com.hninor.pokedexmovil.core.database.entities.toDomain
import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonDetailResponse
import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PokemonLocalDataSource(private val pokemonDao: PokemonDao) {

    suspend fun savePokemonList(
        pokemonList: List<PokemonDetailResponse>,
        hasNext: Boolean,
        offset: Int
    ) {
        val pokemonEntities = pokemonList.map { pokemon ->
            PokemonEntity(
                id = pokemon.id,
                name = pokemon.name,
                imageUrl = pokemon.sprites.other.officialArtwork.front_default,
                types = pokemon.types.joinToString(",") { it.type.name }
            )
        }

        val statEntities = pokemonList.flatMap { pokemon ->
            pokemon.stats.map { stat ->
                PokemonStatEntity(
                    statId = 0,
                    pokemonId = pokemon.id,
                    name = stat.stat.name,
                    value = stat.base_stat
                )
            }
        }

        val abilityEntities = pokemonList.flatMap { pokemon ->
            pokemon.abilities.map { ability ->
                PokemonAbilityEntity(
                    abilityId = 0,
                    pokemonId = pokemon.id,
                    abilityName = ability.ability.name
                )
            }
        }


        val spriteEntities = pokemonList.flatMap { response ->
            listOfNotNull(
                response.sprites.front_default,
                response.sprites.back_default,
                response.sprites.other.showdown.front_default,
                response.sprites.other.showdown.back_default
            ).map { spriteUrl ->
                PokemonSpriteEntity(pokemonId = response.id, spriteUrl = spriteUrl)
            }
        }


        pokemonDao.insertPokemon(pokemonEntities)
        pokemonDao.insertStats(statEntities)
        pokemonDao.insertAbilities(abilityEntities)
        pokemonDao.insertSprites(spriteEntities)

        pokemonDao.insertPokemonPage(PokemonPageEntity(offset, hasNext))
    }

    fun getCachedPokemonList(limit: Int, offset: Int): Flow<List<Pokemon>> {
        return pokemonDao.getPokemonList(limit, offset).map { cachedList ->
            cachedList.map { it.toDomain() }
        }
    }

    suspend fun hasNextPage(offset: Int): Boolean {
        return pokemonDao.hasNextPage(offset) ?: true
    }
}


