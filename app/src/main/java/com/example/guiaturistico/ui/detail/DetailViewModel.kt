package com.example.guiaturistico.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.guiaturistico.data.PlacesRepository
import com.example.guiaturistico.di.ServiceLocator
import com.example.sdui.sdui.SduiPage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repo: PlacesRepository = ServiceLocator.placesRepository()
) : ViewModel() {

    private val _page = MutableStateFlow<SduiPage?>(null)
    val page: StateFlow<SduiPage?> = _page

    fun load(placeId: String) {
        viewModelScope.launch {
            _page.value = repo.sduiDetail(placeId)
        }
    }
}
