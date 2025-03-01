package com.hninor.pokedexmovil.features.pokemon.domain.use_cases

import com.hninor.pokedexmovil.features.pokemon.domain.model.Pokemon

class FilterPokemonUseCase {
    operator fun invoke(pokemonList: List<Pokemon>, query: String): List<Pokemon> {
        if (query.isBlank()) return pokemonList

        return pokemonList.filter { pokemon ->
            pokemon.name.contains(query, ignoreCase = true) ||
                    pokemon.types.any { type -> type.contains(query, ignoreCase = true) }
        }
    }
}