package com.example.sdui.ui


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdui.data.ApiService
import com.example.sdui.sdui.SduiPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed interface UiState {
    data object Loading : UiState
    data class Success(val page: SduiPage) : UiState
    data class Error(val message: String) : UiState
}

class SduiViewModel(
    private val api: ApiService = ApiService.create()
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state

    fun loadHome(segment: String? = "default") {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val page = api.getHome(segment)
                _state.value = UiState.Success(page)
            } catch (e: Exception) {
                _state.value = UiState.Error("Falha ao carregar Home: ${e.message}")
            }
        }
    }

    fun loadDetail(id: String) {
        _state.value = UiState.Loading
        viewModelScope.launch {
            try {
                val page = api.getDetail(id)
                _state.value = UiState.Success(page)
            } catch (e: Exception) {
                _state.value = UiState.Error("Falha ao carregar Detalhe: ${e.message}")
            }
        }
    }
}
