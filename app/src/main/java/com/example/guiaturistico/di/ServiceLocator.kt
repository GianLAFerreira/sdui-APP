package com.example.guiaturistico.di

import com.example.guiaturistico.data.local.AppDatabase
import com.example.guiaturistico.data.local.DatabaseProvider
import com.example.guiaturistico.data.PlacesRepository
import com.example.sdui.data.ApiService

object ServiceLocator {
    val api: ApiService = RetrofitModule.api
    val db: AppDatabase get() = DatabaseProvider.db()

    fun placesRepository() = PlacesRepository(api, db)
}