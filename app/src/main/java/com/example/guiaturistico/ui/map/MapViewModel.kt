package com.example.guiaturistico.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaturistico.data.dto.PlaceDto
import com.example.guiaturistico.data.PlacesRepository
import com.example.guiaturistico.di.RetrofitModule
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel(
    private val repo: PlacesRepository = PlacesRepository(RetrofitModule.api)
) : ViewModel() {

    private val _places = MutableStateFlow<List<PlaceDto>>(emptyList())
    val places: StateFlow<List<PlaceDto>> = _places

    fun nearby(lat: Double, lng: Double, radius: Int = 3000) {
        viewModelScope.launch {
            _places.value = repo.nearby(lat, lng, radius, 20)
        }
    }
}
