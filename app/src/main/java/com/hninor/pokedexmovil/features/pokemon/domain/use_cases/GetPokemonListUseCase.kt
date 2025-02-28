package com.hninor.pokedexmovil.features.pokemon.domain.use_cases

import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import com.hninor.pokedexmovil.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow

class GetPokemonListUseCase(private val repository: PokemonRepository) {

    operator fun invoke(offset: Int, limit: Int): Flow<Result<PokemonListResult>> {
        return repository.getPokemonList(offset, limit)
    }
}