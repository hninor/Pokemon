package com.hninor.pokedexmovil.features.pokemon.data.repository

import com.hninor.pokedexmovil.features.pokemon.data.remote.PokemonRemoteDataSource
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import com.hninor.pokedexmovil.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class PokemonRepositoryImpl(private val pokemonRemoteDataSource: PokemonRemoteDataSource) :
    PokemonRepository {

    override fun getPokemonList(offset: Int, limit: Int): Flow<Result<PokemonListResult>> {
        return pokemonRemoteDataSource.getPokemonList(offset, limit)
    }

}