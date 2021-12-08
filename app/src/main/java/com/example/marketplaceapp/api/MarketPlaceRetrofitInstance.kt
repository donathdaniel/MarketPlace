package com.example.marketplaceapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object MarketPlaceRetrofitInstance {
    private const val BASE_URL = "https://pure-gorge-51703.herokuapp.com/"

    private val retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .callTimeout(2000, TimeUnit.MINUTES)
            .connectTimeout(2000, TimeUnit.SECONDS)
            .readTimeout(3000, TimeUnit.SECONDS)
            .writeTimeout(3000, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder().baseUrl(BASE_URL).client(httpClient).addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val marketPlaceAPI: MarketPlaceAPI by lazy {
        retrofit.create(MarketPlaceAPI::class.java)
    }
}