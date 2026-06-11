package com.diogobaptista.feature.detail.presentation

import app.cash.turbine.test
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.usecase.GetBreedDetailUseCase
import com.diogobaptista.core.domain.usecase.ToggleFavouriteUseCase
import androidx.lifecycle.SavedStateHandle
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class DetailViewModelTest {

    private val getBreedDetailUseCase: GetBreedDetailUseCase = mockk()
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher()

    private val fakeBreed = Breed(
        id = "siam",
        name = "Siamese",
        origin = "Thailand",
        temperament = "Active",
        description = "A friendly cat",
        lifeSpan = "12 - 15",
        imageUrl = null,
        isFavourite = false
    )

    private fun createViewModel(breedId: String = "siam"): DetailViewModel {
        val savedStateHandle = SavedStateHandle(mapOf("breedId" to breedId))
        return DetailViewModel(getBreedDetailUseCase, toggleFavouriteUseCase, savedStateHandle)
    }

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState starts as Loading`() = runTest {
        coEvery { getBreedDetailUseCase(any()) } returns fakeBreed
        val viewModel = createViewModel()
        viewModel.uiState.test {
            assertTrue(awaitItem() is DetailUiState.Loading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState is Success when breed found`() = runTest {
        coEvery { getBreedDetailUseCase("siam") } returns fakeBreed
        val viewModel = createViewModel()
        viewModel.uiState.test {
            assertTrue(awaitItem() is DetailUiState.Loading)
            testDispatcher.scheduler.advanceUntilIdle()
            val success = awaitItem() as DetailUiState.Success
            assertEquals(fakeBreed, success.breed)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `uiState is Error when breed not found`() = runTest {
        coEvery { getBreedDetailUseCase("99") } returns null
        val viewModel = createViewModel(breedId = "99")
        viewModel.uiState.test {
            assertTrue(awaitItem() is DetailUiState.Loading)
            testDispatcher.scheduler.advanceUntilIdle()
            assertTrue(awaitItem() is DetailUiState.Error)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavourite inverts isFavourite in state`() = runTest {
        coEvery { getBreedDetailUseCase("siam") } returns fakeBreed
        val viewModel = createViewModel()
        testDispatcher.scheduler.advanceUntilIdle()
        viewModel.toggleFavourite()
        testDispatcher.scheduler.advanceUntilIdle()
        val state = viewModel.uiState.value as DetailUiState.Success
        assertEquals(true, state.breed.isFavourite)
    }
}