package com.diogobaptista.core.domain.usecase

import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.repository.BreedRepository
import javax.inject.Inject

class GetBreedDetailUseCase @Inject constructor(
    private val repository: BreedRepository
) {
    suspend operator fun invoke(id: String): Breed? =
        repository.getBreedById(id)
}