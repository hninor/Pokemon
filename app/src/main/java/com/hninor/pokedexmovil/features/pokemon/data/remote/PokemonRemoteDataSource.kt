package com.hninor.pokedexmovil.features.pokemon.data.remote

import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
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
                    types = response.types.map { it.type.name }
                )
            }
            emit(Result.success(PokemonListResult(details, hasNextPage)))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}