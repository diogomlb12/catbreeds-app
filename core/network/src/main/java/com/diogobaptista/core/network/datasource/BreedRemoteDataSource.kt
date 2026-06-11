package com.diogobaptista.core.network.datasource

import com.diogobaptista.core.network.api.CatApiService
import com.diogobaptista.core.network.dto.BreedDto
import javax.inject.Inject

class BreedRemoteDataSource @Inject constructor(
    private val api: CatApiService
) {
    suspend fun getBreeds(page: Int, limit: Int): List<BreedDto> =
        api.getBreeds(limit = limit, page = page)

    suspend fun searchBreeds(query: String): List<BreedDto> =
        api.searchBreeds(query)
}