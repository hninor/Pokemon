package com.hninor.pokedexmovil.features.pokemon.data.remote

import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonDetailResponse
import com.hninor.pokedexmovil.features.pokemon.data.model.PokemonListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PokeApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonListResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonDetail(@Path("id") id: Int): PokemonDetailResponse
}

