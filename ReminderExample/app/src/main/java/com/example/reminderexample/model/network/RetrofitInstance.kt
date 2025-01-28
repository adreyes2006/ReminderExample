package com.example.reminderexample.model.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL =
        "https://e9ec14e2-9ec7-4fbf-bdbf-7ecdd05a4cd7.mock.pstmn.io/"
    val api: ApiService by lazy {
        Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
