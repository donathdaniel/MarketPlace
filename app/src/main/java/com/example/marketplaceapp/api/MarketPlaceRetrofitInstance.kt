package com.example.marketplaceapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MarketPlaceRetrofitInstance {
    private const val BASE_URL = "https://pure-gorge-51703.herokuapp.com/"

    private val retrofit by lazy {
        Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val marketPlaceAPI: MarketPlaceAPI by lazy {
        retrofit.create(MarketPlaceAPI::class.java)
    }
}