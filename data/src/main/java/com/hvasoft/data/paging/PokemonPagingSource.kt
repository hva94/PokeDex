package com.hvasoft.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hvasoft.data.mapper.toDomain
import com.hvasoft.data.networking.PokemonApi
import com.hvasoft.domain.model.Pokemon

class PokemonPagingSource(
    private val pokemonApi: PokemonApi,
    private val limit: Int,
    private val offset: Int
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val currentPage = params.key ?: 1
        return try {
            val response = pokemonApi.getPokemons(limit = limit, offset = offset)
            if (response.isSuccessful && response.body() != null) {
                val responseBody = response.body()!!
                val pokemons: List<Pokemon> = responseBody.result.map { it.toDomain() }
                val endOfPaginationReached = pokemons.isEmpty()

                LoadResult.Page(
                    data = pokemons,
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Error(Exception("Error"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}