package com.diogobaptista.core.domain.usecase

import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(
    private val repository: BreedRepository
) {
    operator fun invoke(): Flow<List<Breed>> =
        repository.getFavourites()
}