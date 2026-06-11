package com.diogobaptista.core.domain.usecase

import com.diogobaptista.core.domain.repository.BreedRepository
import javax.inject.Inject

class ToggleFavouriteUseCase @Inject constructor(
    private val repository: BreedRepository
) {
    suspend operator fun invoke(breedId: String, isFavourite: Boolean) =
        repository.toggleFavourite(breedId, isFavourite)
}