package com.hninor.pokedexmovil.features.pokemon.data.remote

import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonListResultDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PokemonRemoteDataSource(private val api: PokeApiService) {


    fun getPokemonList(offset: Int, limit: Int): Flow<Result<PokemonListResultDTO>> = flow {
        try {
            val listResponse = api.getPokemonList(limit = limit, offset = offset)

            val hasNextPage = listResponse.next != null

            val details = listResponse.results.map { pokemon ->
                val id = pokemon.url.split("/").dropLast(1).last().toInt()
                api.getPokemonDetail(id)
            }
            emit(Result.success(PokemonListResultDTO(details, hasNextPage)))

        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)
}