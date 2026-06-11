package com.diogobaptista.core.database.di

import android.content.Context
import androidx.room.Room
import com.diogobaptista.core.database.CatBreedsDatabase
import com.diogobaptista.core.database.dao.BreedDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): CatBreedsDatabase =
        Room.databaseBuilder(
            context,
            CatBreedsDatabase::class.java,
            "catbreeds.db"
        ).build()

    @Provides
    @Singleton
    fun provideBreedDao(database: CatBreedsDatabase): BreedDao =
        database.breedDao()
}