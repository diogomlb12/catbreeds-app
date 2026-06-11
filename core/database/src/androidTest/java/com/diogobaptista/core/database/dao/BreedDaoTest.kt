package com.diogobaptista.core.database.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.diogobaptista.core.database.CatBreedsDatabase
import com.diogobaptista.core.database.entity.BreedEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BreedDaoTest {

    private lateinit var db: CatBreedsDatabase
    private lateinit var dao: BreedDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            CatBreedsDatabase::class.java
        ).build()
        dao = db.breedDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    // --- upsert + getBreedById ---

    @Test
    fun upsertAndGetBreedById() = runTest {
        dao.upsertBreed(fakeEntity)

        val result = dao.getBreedById("1")

        assertEquals(fakeEntity, result)
    }

    @Test
    fun getBreedById_returnsNull_whenNotFound() = runTest {
        val result = dao.getBreedById("999")

        assertNull(result)
    }

    // --- favourites ---

    @Test
    fun getFavouriteBreeds_onlyReturnsFavourites() = runTest {
        dao.upsertBreeds(fakeEntityList)

        dao.getFavouriteBreeds().test {
            val favourites = awaitItem()
            assertTrue(favourites.all { it.isFavourite })
            assertEquals(1, favourites.size)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun updateFavourite_updatesCorrectly() = runTest {
        dao.upsertBreed(fakeEntity) // isFavourite = false

        dao.updateFavourite("1", true)

        val result = dao.getBreedById("1")
        assertTrue(result?.isFavourite == true)
    }

    // --- upsert comporta-se como update ---

    @Test
    fun upsertBreed_updatesExisting() = runTest {
        dao.upsertBreed(fakeEntity)
        dao.upsertBreed(fakeEntity.copy(name = "Siamese Updated"))

        val result = dao.getBreedById("1")
        assertEquals("Siamese Updated", result?.name)
    }

    // --- clearAll ---

    @Test
    fun clearAll_removesAllBreeds() = runTest {
        dao.upsertBreeds(fakeEntityList)
        dao.clearAll()

        val result = dao.getBreedById("1")
        assertNull(result)
    }
}