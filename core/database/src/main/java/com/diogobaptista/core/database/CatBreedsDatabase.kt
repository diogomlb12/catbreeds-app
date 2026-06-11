package com.diogobaptista.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diogobaptista.core.database.dao.BreedDao
import com.diogobaptista.core.database.entity.BreedEntity

@Database(
    entities = [BreedEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CatBreedsDatabase : RoomDatabase() {
    abstract fun breedDao(): BreedDao
}