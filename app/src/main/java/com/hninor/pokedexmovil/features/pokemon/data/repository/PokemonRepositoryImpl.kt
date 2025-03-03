package com.hninor.pokedexmovil.features.pokemon.data.repository

import com.hninor.pokedexmovil.features.pokemon.data.local.PokemonLocalDataSource
import com.hninor.pokedexmovil.features.pokemon.data.model.asDomain
import com.hninor.pokedexmovil.features.pokemon.data.remote.PokemonRemoteDataSource
import com.hninor.pokedexmovil.features.pokemon.domain.model.PokemonListResult
import com.hninor.pokedexmovil.features.pokemon.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow

class PokemonRepositoryImpl(
    private val pokemonRemoteDataSource: PokemonRemoteDataSource,
    private val pokemonLocalDataSource: PokemonLocalDataSource
) :
    PokemonRepository {

    override fun getPokemonList(offset: Int, limit: Int, forceRefresh: Boolean): Flow<Result<PokemonListResult>> = flow {

        if (forceRefresh) {
            pokemonLocalDataSource.deleteAll()
        }

        val cachedList = pokemonLocalDataSource.getCachedPokemonList(limit, offset).firstOrNull()

        if (!cachedList.isNullOrEmpty()) {
            emit(
                Result.success(
                    PokemonListResult(
                        cachedList,
                        hasNextPage = pokemonLocalDataSource.hasNextPage(offset)
                    )
                )
            )
        } else {
            pokemonRemoteDataSource.getPokemonList(offset, limit).collect { result ->
                if (result.isSuccess) {
                    val pokemonList = result.getOrNull()?.pokemonList ?: emptyList()
                    val hasNextPage = result.getOrNull()?.hasNextPage ?: true

                    pokemonLocalDataSource.savePokemonList(pokemonList, hasNextPage, offset)

                    emit(Result.success(PokemonListResult(pokemonList.asDomain(), hasNextPage)))

                } else if (result.isFailure) {
                    emit(Result.failure(result.exceptionOrNull() ?: Exception("Unknown error")))
                }
            }
        }

    }.distinctUntilChanged()

}