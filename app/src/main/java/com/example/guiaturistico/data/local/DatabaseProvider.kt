package com.example.guiaturistico.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var instance: AppDatabase? = null

    fun init(context: Context) {
        if (instance == null) {
            synchronized(this) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext, AppDatabase::class.java, "app.db"
                    ).build()
                }
            }
        }
    }

    fun db(): AppDatabase = requireNotNull(instance) { "DatabaseProvider não inicializado" }
}
