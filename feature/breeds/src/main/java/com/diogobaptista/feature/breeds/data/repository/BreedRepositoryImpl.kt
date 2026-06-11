package com.diogobaptista.feature.breeds.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.diogobaptista.core.database.CatBreedsDatabase
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.repository.BreedRepository
import com.diogobaptista.core.network.datasource.BreedRemoteDataSource
import com.diogobaptista.feature.breeds.data.mapper.toDomain
import com.diogobaptista.feature.breeds.data.mapper.toEntity
import com.diogobaptista.feature.breeds.data.mediator.BreedRemoteMediator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(
    private val remoteDataSource: BreedRemoteDataSource,
    private val database: CatBreedsDatabase
) : BreedRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getBreeds(query: String): Flow<PagingData<Breed>> {
        if (query.isNotBlank()) {
            return searchBreeds(query)
        }

        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = BreedRemoteMediator(remoteDataSource, database),
            pagingSourceFactory = { database.breedDao().getAllBreeds() }
        ).flow.map { pagingData -> pagingData.map { it.toDomain() } }
    }

    private fun searchBreeds(query: String): Flow<PagingData<Breed>> {
        return Pager(
            config = PagingConfig(pageSize = 20)
        ) {
            SearchPagingSource(remoteDataSource, query)
        }.flow
    }

    override suspend fun getBreedById(id: String): Breed? =
        database.breedDao().getBreedById(id)?.toDomain()

    override fun getFavourites(): Flow<List<Breed>> =
        database.breedDao().getFavouriteBreeds().map { list -> list.map { it.toDomain() } }

    override suspend fun toggleFavourite(breedId: String, isFavourite: Boolean) =
        database.breedDao().updateFavourite(breedId, isFavourite)
}