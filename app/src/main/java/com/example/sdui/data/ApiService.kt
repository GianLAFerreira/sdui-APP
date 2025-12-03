package com.example.sdui.data

import com.example.guiaturistico.data.dto.PlaceDetailDto
import com.example.guiaturistico.data.dto.PlaceDto
import com.example.sdui.sdui.SduiPage
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/api/places/popular")
    suspend fun popular(
        @Query("city") city: String,
        @Query("limit") limit: Int = 20
    ): List<PlaceDto>

    @GET("/api/places/search")
    suspend fun search(
        @Query("q") q: String,
        @Query("city") city: String? = null,
        @Query("limit") limit: Int = 20
    ): List<PlaceDto>

    @GET("/api/places/nearby")
    suspend fun nearby(
        @Query("lat") lat: Double,
        @Query("lng") lng: Double,
        @Query("radius") radius: Int = 3000,
        @Query("limit") limit: Int = 20
    ): List<PlaceDto>

    @GET("/api/places/{placeId}")
    suspend fun detail(@Path("placeId") id: String): PlaceDetailDto

    @GET("/sdui/home")
    suspend fun sduiHome(@Query("segment") segment: String? = null): SduiPage

    @GET("/sdui/detail/{placeId}")
    suspend fun sduiDetail(@Path("placeId") id: String): SduiPage
}