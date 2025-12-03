package com.example.guiaturistico.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaturistico.data.dto.PlaceDto
import com.example.guiaturistico.data.PlacesRepository
import com.example.guiaturistico.di.RetrofitModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListViewModel(
    private val repo: PlacesRepository = PlacesRepository(RetrofitModule.api)
) : ViewModel() {

    private val _state = MutableStateFlow<List<PlaceDto>>(emptyList())
    val state: StateFlow<List<PlaceDto>> = _state

    fun load(city: String = "Sao Paulo") {
        viewModelScope.launch {
            _state.value = repo.popular(city, 20)
        }
    }

    fun search(q: String, city: String?) {
        viewModelScope.launch {
            _state.value = repo.search(q, city, 20)
        }
    }
}
