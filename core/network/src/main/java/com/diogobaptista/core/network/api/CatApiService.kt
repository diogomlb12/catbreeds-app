package com.diogobaptista.core.network.api

import com.diogobaptista.core.network.dto.BreedDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("breeds")
    suspend fun getBreeds(
        @Query("limit") limit: Int = 10,
        @Query("page") page: Int = 0
    ): List<BreedDto>

    @GET("breeds/search")
    suspend fun searchBreeds(
        @Query("q") query: String
    ): List<BreedDto>
}