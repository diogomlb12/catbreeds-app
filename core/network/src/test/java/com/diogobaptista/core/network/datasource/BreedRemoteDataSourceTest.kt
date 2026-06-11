package com.diogobaptista.core.network.datasource

import com.diogobaptista.core.network.api.CatApiService
import com.diogobaptista.core.network.fakeBreedDto
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BreedRemoteDataSourceTest {

    private val api: CatApiService = mockk()
    private lateinit var dataSource: BreedRemoteDataSource

    @Before
    fun setup() {
        dataSource = BreedRemoteDataSource(api)
    }

    @Test
    fun `getBreeds returns list from api`() = runTest {
        coEvery { api.getBreeds(any(), any()) } returns listOf(fakeBreedDto)

        val result = dataSource.getBreeds(page = 0, limit = 10)

        assertEquals(listOf(fakeBreedDto), result)
    }

    @Test
    fun `getBreeds calls api with correct params`() = runTest {
        coEvery { api.getBreeds(any(), any()) } returns emptyList()

        dataSource.getBreeds(page = 2, limit = 10)

        coVerify(exactly = 1) { api.getBreeds(limit = 10, page = 2) }
    }

    @Test
    fun `searchBreeds returns list from api`() = runTest {
        coEvery { api.searchBreeds(any()) } returns listOf(fakeBreedDto)

        val result = dataSource.searchBreeds("siamese")

        assertEquals(listOf(fakeBreedDto), result)
    }

    @Test
    fun `searchBreeds calls api with correct query`() = runTest {
        coEvery { api.searchBreeds(any()) } returns emptyList()

        dataSource.searchBreeds("ragdoll")

        coVerify(exactly = 1) { api.searchBreeds("ragdoll") }
    }

    @Test
    fun `getBreeds returns empty list when api returns empty`() = runTest {
        coEvery { api.getBreeds(any(), any()) } returns emptyList()

        val result = dataSource.getBreeds(page = 0, limit = 10)

        assertEquals(emptyList<Nothing>(), result)
    }
}