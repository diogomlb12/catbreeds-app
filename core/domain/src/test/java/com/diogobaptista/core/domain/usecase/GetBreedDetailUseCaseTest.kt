package com.diogobaptista.core.domain.usecase

import com.diogobaptista.core.domain.fakeBreed
import com.diogobaptista.core.domain.repository.BreedRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class GetBreedDetailUseCaseTest {

    private val repository: BreedRepository = mockk()
    private lateinit var useCase: GetBreedDetailUseCase

    @Before
    fun setup() {
        useCase = GetBreedDetailUseCase(repository)
    }

    @Test
    fun `returns breed when found`() = runTest {
        coEvery { repository.getBreedById("1") } returns fakeBreed

        val result = useCase("1")

        assertEquals(fakeBreed, result)
    }

    @Test
    fun `returns null when breed not found`() = runTest {
        coEvery { repository.getBreedById("99") } returns null

        val result = useCase("99")

        assertNull(result)
    }

    @Test
    fun `calls repository with correct id`() = runTest {
        coEvery { repository.getBreedById(any()) } returns fakeBreed

        useCase("abc")

        coVerify(exactly = 1) { repository.getBreedById("abc") }
    }
}