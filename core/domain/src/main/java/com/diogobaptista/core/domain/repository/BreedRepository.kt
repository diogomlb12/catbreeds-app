package com.diogobaptista.core.domain.repository

import androidx.paging.PagingData
import com.diogobaptista.core.domain.model.Breed
import kotlinx.coroutines.flow.Flow

interface BreedRepository {
    fun getBreeds(query: String = ""): Flow<PagingData<Breed>>
    suspend fun getBreedById(id: String): Breed?
    fun getFavourites(): Flow<List<Breed>>
    suspend fun toggleFavourite(breedId: String, isFavourite: Boolean)
}