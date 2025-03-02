package com.hninor.pokedexmovil.features.pokemon.data.remote

import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonStat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRemoteDataSource(private val api: PokeApiService) {


    fun getPokemonList(offset: Int, limit: Int): Flow<Result<PokemonListResult>> = flow {
        try {
            val listResponse = api.getPokemonList(limit = limit, offset = offset)

            val hasNextPage = listResponse.next != null

            val details = listResponse.results.map { pokemon ->
                val id = pokemon.url.split("/").dropLast(1).last().toInt()
                val response = api.getPokemonDetail(id)
                Pokemon(
                    id = response.id,
                    name = response.name,
                    imageUrl = response.sprites.other.officialArtwork.front_default,
                    types = response.types.map { it.type.name },
                    sprites = listOfNotNull(
                        response.sprites.front_default,
                        response.sprites.back_default,
                        response.sprites.other.showdown.front_default,
                        response.sprites.other.showdown.back_default
                    ),
                    stats = response.stats.map { PokemonStat(it.stat.name, it.base_stat) },
                    abilities = response.abilities.map { it.ability.name },
                )
            }
            emit(Result.success(PokemonListResult(details, hasNextPage)))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}