package com.diogobaptista.feature.favourites.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diogobaptista.core.domain.usecase.GetFavouritesUseCase
import com.diogobaptista.core.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<FavouritesUiState>(FavouritesUiState.Loading)
    val uiState: StateFlow<FavouritesUiState> = _uiState

    init {
        observeFavourites()
    }

    private fun observeFavourites() {
        viewModelScope.launch {
            getFavouritesUseCase().collect { breeds ->
                _uiState.value = if (breeds.isEmpty()) {
                    FavouritesUiState.Empty
                } else {
                    FavouritesUiState.Success(
                        breeds = breeds,
                        averageLifeSpan = calculateAverageLifeSpan(breeds.map { it.lifeSpan })
                    )
                }
            }
        }
    }

    fun toggleFavourite(breedId: String, isFavourite: Boolean) {
        viewModelScope.launch {
            toggleFavouriteUseCase(breedId, isFavourite)
        }
    }

    private fun calculateAverageLifeSpan(lifeSpans: List<String>): Double {
        val values = lifeSpans.mapNotNull { span ->
            span.split("-").firstOrNull()?.trim()?.toDoubleOrNull()
        }
        return if (values.isEmpty()) 0.0 else values.average()
    }
}