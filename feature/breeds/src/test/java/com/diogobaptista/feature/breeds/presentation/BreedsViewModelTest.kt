package com.diogobaptista.feature.breeds.presentation

import androidx.paging.PagingData
import app.cash.turbine.test
import com.diogobaptista.core.domain.model.Breed
import com.diogobaptista.core.domain.usecase.GetBreedsUseCase
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BreedsViewModelTest {

    private val getBreedsUseCase: GetBreedsUseCase = mockk()
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase = mockk(relaxed = true)
    private lateinit var viewModel: BreedsViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { getBreedsUseCase(any()) } returns flowOf(PagingData.empty())
        viewModel = BreedsViewModel(getBreedsUseCase, toggleFavouriteUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchQuery starts empty`() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onSearchQueryChanged updates searchQuery`() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())
            viewModel.onSearchQueryChanged("siamese")
            assertEquals("siamese", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `toggleFavourite calls use case with correct params`() = runTest {
        viewModel.toggleFavourite("siam", true)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { toggleFavouriteUseCase("siam", true) }
    }
}