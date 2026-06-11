package com.diogobaptista.core.domain.usecase

import androidx.paging.PagingData
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.repository.BreedRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBreedsUseCase @Inject constructor(
    private val repository: BreedRepository
) {
    operator fun invoke(query: String = ""): Flow<PagingData<Breed>> =
        repository.getBreeds(query)
}