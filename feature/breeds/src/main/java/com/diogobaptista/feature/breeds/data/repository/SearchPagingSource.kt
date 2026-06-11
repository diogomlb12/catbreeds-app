package com.diogobaptista.feature.breeds.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.network.datasource.BreedRemoteDataSource
import com.diogobaptista.feature.breeds.data.mapper.toDomain
import com.diogobaptista.feature.breeds.data.mapper.toEntity

class SearchPagingSource(
    private val remoteDataSource: BreedRemoteDataSource,
    private val query: String
) : PagingSource<Int, Breed>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Breed> {
        return try {
            val results = remoteDataSource.searchBreeds(query)
            LoadResult.Page(
                data = results.map { it.toEntity().toDomain() },
                prevKey = null,
                nextKey = null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Breed>): Int? = null
}