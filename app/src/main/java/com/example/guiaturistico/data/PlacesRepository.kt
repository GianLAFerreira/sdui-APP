package com.example.guiaturistico.data

import com.example.guiaturistico.data.dto.PlaceDetailDto
import com.example.guiaturistico.data.dto.PlaceDto
import com.example.guiaturistico.data.local.AppDatabase
import com.example.guiaturistico.data.local.FavoriteEntity
import com.example.sdui.data.ApiService
import kotlinx.coroutines.flow.Flow

class PlacesRepository(
    private val api: ApiService,
    private val db: AppDatabase
) {
    private val favorites = db.favoriteDao()

    suspend fun popular(city: String, limit: Int) = api.popular(city, limit)
    suspend fun search(q: String, city: String?, limit: Int) = api.search(q, city, limit)
    suspend fun nearby(lat: Double, lng: Double, radius: Int, limit: Int) = api.nearby(lat, lng, radius, limit)
    suspend fun detail(id: String): PlaceDetailDto = api.detail(id)
    suspend fun sduiDetail(id: String) = api.sduiDetail(id)

    fun favorites(): Flow<List<FavoriteEntity>> = favorites.getAll()
    fun isFavorite(id: String): Flow<Boolean> = favorites.isFavorite(id)
    suspend fun toggleFavorite(place: PlaceDto) {
        favorites.upsert(
            FavoriteEntity(
                id = place.id,
                name = place.name,
                address = place.address,
                lat = place.coordinates?.lat,
                lng = place.coordinates?.lng,
                imageUrl = place.photos?.firstOrNull()?.url
            )
        )
    }
}