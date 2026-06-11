package com.diogobaptista.feature.breeds.di

import com.diogobaptista.core.domain.repository.BreedRepository
import com.diogobaptista.feature.breeds.data.repository.BreedRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class BreedsModule {

    @Binds
    @Singleton
    abstract fun bindBreedRepository(impl: BreedRepositoryImpl): BreedRepository
}