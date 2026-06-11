package com.diogobaptista.feature.breeds.presentation

sealed class BreedsUiState {
    object Loading : BreedsUiState()
    object Success : BreedsUiState()
    data class Error(val message: String) : BreedsUiState()
}