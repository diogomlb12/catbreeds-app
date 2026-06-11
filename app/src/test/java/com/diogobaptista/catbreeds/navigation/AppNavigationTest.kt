package com.diogobaptista.catbreeds.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class AppNavigationTest {

    @Test
    fun `breeds route is correct`() {
        assertEquals("breeds", Screen.Breeds.route)
    }

    @Test
    fun `favourites route is correct`() {
        assertEquals("favourites", Screen.Favourites.route)
    }

    @Test
    fun `detail route contains breedId param`() {
        assertEquals("detail/{breedId}", Screen.Detail.route)
    }

    @Test
    fun `detail createRoute with breedId generates correct route`() {
        assertEquals("detail/siam", Screen.Detail.createRoute("siam"))
    }

    @Test
    fun `detail createRoute with different breedId generates correct route`() {
        assertEquals("detail/ragd", Screen.Detail.createRoute("ragd"))
    }
}