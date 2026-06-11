package com.diogobaptista.core.domain.usecase

import app.cash.turbine.test
import com.diogobaptista.core.domain.fakeBreedList
import com.diogobaptista.core.domain.repository.BreedRepository
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetFavouritesUseCaseTest {

    private val repository: BreedRepository = mockk()
    private lateinit var useCase: GetFavouritesUseCase

    @Before
    fun setup() {
        useCase = GetFavouritesUseCase(repository)
    }

    @Test
    fun `emits list of favourites`() = runTest {
        val favourites = fakeBreedList.filter { it.isFavourite }
        every { repository.getFavourites() } returns flowOf(favourites)

        useCase().test {
            assertEquals(favourites, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `emits empty list when no favourites`() = runTest {
        every { repository.getFavourites() } returns flowOf(emptyList())

        useCase().test {
            assertEquals(emptyList<Any>(), awaitItem())
            awaitComplete()
        }
    }
}