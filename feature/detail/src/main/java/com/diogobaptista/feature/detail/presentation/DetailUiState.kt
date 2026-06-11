package com.diogobaptista.feature.detail.presentation

import com.diogobaptista.core.domain.model.Breed

sealed class DetailUiState {
    object Loading : DetailUiState()
    data class Success(val breed: Breed) : DetailUiState()
    data class Error(val message: String) : DetailUiState()
}