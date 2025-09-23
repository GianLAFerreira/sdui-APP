package com.example.sdui.data

import com.example.sdui.sdui.SduiPage
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/sdui/home")
    suspend fun getHome(@Query("segment") segment: String? = null): SduiPage

    @GET("/sdui/detail/{id}")
    suspend fun getDetail(@Path("id") id: String): SduiPage

    companion object {
        fun create(baseUrl: String = "http://localhost:8081"): ApiService {
            val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
            val client = OkHttpClient.Builder().addInterceptor(logging).build()

            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create(moshi).withNullSerialization())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}