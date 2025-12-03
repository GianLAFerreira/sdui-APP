package com.example.guiaturistico

import android.app.Application
import com.example.guiaturistico.data.local.DatabaseProvider

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.init(this)
    }
}