package com.diogobaptista.feature.favourites.presentation

import app.cash.turbine.test
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.usecase.GetFavouritesUseCase
import com.diogobaptista.core.domain.usecase.ToggleFavouriteUseCase
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FavouritesViewModelTest {

    private val getFavouritesUseCase: GetFavouritesUseCase = mockk()
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase = mockk(relaxed = true)
    private lateinit var viewModel: FavouritesViewModel
    private val testDispatcher = StandardTestDispatcher()

    private val fakeBreeds = listOf(
        Breed("1", "Siamese", "Thailand", "Active", "Friendly", "12 - 15", null, true),
        Breed("2", "Ragdoll", "USA", "Gentle", "Calm", "12 - 17", null, true)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState is Empty when no favourites`() = runTest {
        every { getFavouritesUseCase() } returns flowOf(emptyList())
        viewModel = FavouritesViewModel(getFavouritesUseCase, toggleFavouriteUseCase)
        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val items = cancelAndConsumeRemainingEvents()
            assertTrue(items.any { it.toString().contains("Empty") })
        }
    }

    @Test
    fun `uiState is Success when favourites exist`() = runTest {
        every { getFavouritesUseCase() } returns flowOf(fakeBreeds)
        viewModel = FavouritesViewModel(getFavouritesUseCase, toggleFavouriteUseCase)
        viewModel.uiState.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val state = expectMostRecentItem() as FavouritesUiState.Success
            assertEquals(fakeBreeds, state.breeds)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `averageLifeSpan is calculated correctly`() = runTest {
        every { getFavouritesUseCase() } returns flowOf(fakeBreeds)
        viewModel = FavouritesViewModel(getFavouritesUseCase, toggleFavouriteUseCase)
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiState.value as FavouritesUiState.Success
        assertEquals(12.0, state.averageLifeSpan, 0.01)
    }

    @Test
    fun `toggleFavourite calls use case with correct params`() = runTest {
        every { getFavouritesUseCase() } returns flowOf(fakeBreeds)
        viewModel = FavouritesViewModel(getFavouritesUseCase, toggleFavouriteUseCase)
        viewModel.toggleFavourite("1", false)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { toggleFavouriteUseCase("1", false) }
    }
}