package com.diogobaptista.catbreeds.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.diogobaptista.feature.breeds.presentation.BreedsScreen
import com.diogobaptista.feature.detail.presentation.DetailScreen
import com.diogobaptista.feature.favourites.presentation.FavouritesScreen

sealed class Screen(val route: String) {
    object Breeds : Screen("breeds")
    object Favourites : Screen("favourites")
    object Detail : Screen("detail/{breedId}") {
        fun createRoute(breedId: String) = "detail/$breedId"
    }
}

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Breeds.route,
        modifier = modifier
    ) {
        composable(Screen.Breeds.route) {
            BreedsScreen(
                onBreedClick = { breedId ->
                    navController.navigate(Screen.Detail.createRoute(breedId))
                },
                onFavouritesClick = {
                    navController.navigate(Screen.Favourites.route)
                }
            )
        }
        composable(Screen.Favourites.route) {
            FavouritesScreen(
                onBreedClick = { breedId ->
                    navController.navigate(Screen.Detail.createRoute(breedId))
                },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Detail.route) {
            DetailScreen(onBack = { navController.popBackStack() })
        }
    }
}