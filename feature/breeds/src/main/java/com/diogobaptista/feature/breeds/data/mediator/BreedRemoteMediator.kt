package com.diogobaptista.feature.breeds.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.diogobaptista.core.database.CatBreedsDatabase
import com.diogobaptista.core.database.entity.BreedEntity
import com.diogobaptista.core.network.datasource.BreedRemoteDataSource
import com.diogobaptista.feature.breeds.data.mapper.toEntity
@OptIn(ExperimentalPagingApi::class)
class BreedRemoteMediator(
    private val remoteDataSource: BreedRemoteDataSource,
    private val database: CatBreedsDatabase
) : RemoteMediator<Int, BreedEntity>() {

    private var currentPage = 0

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BreedEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    currentPage = 0
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    currentPage++
                    currentPage
                }
            }

            val breeds = remoteDataSource.getBreeds(
                page = page,
                limit = state.config.pageSize
            )

            val dao = database.breedDao()
            val entities = breeds.map { dto ->
                val existing = dao.getBreedById(dto.id)
                dto.toEntity().copy(isFavourite = existing?.isFavourite ?: false)
            }
            if (loadType == LoadType.REFRESH) dao.clearAll()
            dao.upsertBreeds(entities)

            MediatorResult.Success(endOfPaginationReached = breeds.isEmpty())
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}