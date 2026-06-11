package com.diogobaptista.feature.favourites.presentation

import com.diogobaptista.core.domain.model.Breed

sealed class FavouritesUiState {
    object Loading : FavouritesUiState()
    object Empty : FavouritesUiState()
    data class Success(
        val breeds: List<Breed>,
        val averageLifeSpan: Double
    ) : FavouritesUiState()
    data class Error(val message: String) : FavouritesUiState()
}