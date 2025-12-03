package com.example.guiaturistico.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val name: String,
    val address: String?,
    val lat: Double?,
    val lng: Double?,
    val imageUrl: String?
)