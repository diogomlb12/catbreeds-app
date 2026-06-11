package com.diogobaptista.feature.detail.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.diogobaptista.core.domain.usecase.GetBreedDetailUseCase
import com.diogobaptista.core.domain.usecase.ToggleFavouriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getBreedDetailUseCase: GetBreedDetailUseCase,
    private val toggleFavouriteUseCase: ToggleFavouriteUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val breedId: String = checkNotNull(savedStateHandle["breedId"])

    private val _uiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val uiState: StateFlow<DetailUiState> = _uiState

    init {
        loadBreed()
    }

    private fun loadBreed() {
        viewModelScope.launch {
            _uiState.value = DetailUiState.Loading
            val breed = getBreedDetailUseCase(breedId)
            _uiState.value = if (breed != null) {
                DetailUiState.Success(breed)
            } else {
                DetailUiState.Error("Breed not found")
            }
        }
    }

    fun toggleFavourite() {
        val current = _uiState.value as? DetailUiState.Success ?: return
        viewModelScope.launch {
            toggleFavouriteUseCase(current.breed.id, !current.breed.isFavourite)
            _uiState.value = current.copy(
                breed = current.breed.copy(isFavourite = !current.breed.isFavourite)
            )
        }
    }
}