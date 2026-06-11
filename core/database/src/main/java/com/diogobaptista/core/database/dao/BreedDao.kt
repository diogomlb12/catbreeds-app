package com.diogobaptista.core.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.diogobaptista.core.database.entity.BreedEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BreedDao {
    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): PagingSource<Int, BreedEntity>

    @Query("SELECT * FROM breeds WHERE isFavourite = 1")
    fun getFavouriteBreeds(): Flow<List<BreedEntity>>

    @Query("SELECT * FROM breeds WHERE id = :id")
    suspend fun getBreedById(id: String): BreedEntity?

    @Upsert
    suspend fun upsertBreeds(breeds: List<BreedEntity>)

    @Upsert
    suspend fun upsertBreed(breed: BreedEntity)

    @Query("UPDATE breeds SET isFavourite = :isFavourite WHERE id = :id")
    suspend fun updateFavourite(id: String, isFavourite: Boolean)

    @Query("DELETE FROM breeds")
    suspend fun clearAll()
}