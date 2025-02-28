package com.hninor.pokedexmovil.features.pokemon.domain.repository

import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import kotlinx.coroutines.flow.Flow


interface PokemonRepository {

    fun getPokemonList(offset: Int, limit: Int): Flow<Result<PokemonListResult>>
}