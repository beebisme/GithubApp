package com.example.githubapp.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    private const val BASE_URL = "https://api.github.com/"
    private var retrofit: Retrofit? = null

    val apiService: ApiService by lazy {
        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit!!.create(ApiService::class.java)
    }
}