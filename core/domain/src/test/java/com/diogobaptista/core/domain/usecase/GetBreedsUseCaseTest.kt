package com.diogobaptista.core.domain.usecase

import androidx.paging.PagingData
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.repository.BreedRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class GetBreedsUseCaseTest {

    private val repository: BreedRepository = mockk()
    private lateinit var useCase: GetBreedsUseCase

    @Before
    fun setup() {
        useCase = GetBreedsUseCase(repository)
    }

    @Test
    fun `returns flow from repository`() {
        every { repository.getBreeds("") } returns flowOf(PagingData.empty())

        val result = useCase()

        assertNotNull(result)
    }

    @Test
    fun `passes query to repository`() {
        every { repository.getBreeds("siamese") } returns flowOf(PagingData.empty())

        useCase("siamese")

        verify(exactly = 1) { repository.getBreeds("siamese") }
    }

    @Test
    fun `uses empty string as default query`() {
        every { repository.getBreeds("") } returns flowOf(PagingData.empty())

        useCase() // sem argumentos

        verify(exactly = 1) { repository.getBreeds("") }
    }
}