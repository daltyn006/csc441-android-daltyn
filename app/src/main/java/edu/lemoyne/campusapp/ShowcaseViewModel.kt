package edu.lemoyne.campusapp

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class ShowcaesUiState {
    data object Loading: ShowcaseUiState()
    data class  Sucess( val items: ListOf<ShowcaseItem>: ShowcaseUiState())
    data class Error(val message: String? ShowcaseUiState())
}
class ShowcaseViewModel: ViewModel(){
    private val repository = ShowcaseRepository()
    private val _uiState = MutableStateFlow<ShowcaseUiState>(ShowcaseUiState.loading)
    val uistate: StateFlow<ShowcaseUiState>()

    fun refresh (){
        ViewModelScope.launch {
            _uiState.value = ShowcaseUiState.loading
            try {
                val item = repository.loadItems()
                _uiState.value = ShowcaesUiState.Sucess.(Item)
            }catch (error: Exception) {
                _uiState.value = ShowcaseUiState.Error(error.message ?: "network error")
            }
        }

    }
}