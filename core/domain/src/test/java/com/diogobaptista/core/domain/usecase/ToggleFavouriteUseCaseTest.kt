package com.diogobaptista.core.domain.usecase

import com.diogobaptista.core.domain.repository.BreedRepository
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ToggleFavouriteUseCaseTest {

    private val repository: BreedRepository = mockk()
    private lateinit var useCase: ToggleFavouriteUseCase

    @Before
    fun setup() {
        useCase = ToggleFavouriteUseCase(repository)
    }

    @Test
    fun `calls repository to mark as favourite`() = runTest {
        coJustRun { repository.toggleFavourite("1", true) }

        useCase("1", true)

        coVerify(exactly = 1) { repository.toggleFavourite("1", true) }
    }

    @Test
    fun `calls repository to remove favourite`() = runTest {
        coJustRun { repository.toggleFavourite("1", false) }

        useCase("1", false)

        coVerify(exactly = 1) { repository.toggleFavourite("1", false) }
    }
}