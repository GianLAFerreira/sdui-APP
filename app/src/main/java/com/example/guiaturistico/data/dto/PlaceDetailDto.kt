package com.example.guiaturistico.data.dto

data class PlaceDetailDto(
    val id: String,
    val name: String,
    val description: String?,
    val coordinates: CoordinatesDto?,
    val address: String?,
    val categories: List<String>?,
    val rating: Double?,
    val photos: List<PhotoDto>?,
    val openingHours: OpeningHoursDto?,
    val website: String?,
    val phone: String?,
    val priceLevel: Int?
)
