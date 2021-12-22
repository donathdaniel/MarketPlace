package com.example.marketplaceapp.api

import com.example.marketplaceapp.utils.Constant
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit


object MarketPlaceRetrofitInstance {
    private const val BASE_URL = Constant.marketPlaceAPIBaseUrl

    private val retrofit by lazy {
        val httpClient = OkHttpClient.Builder()
            .callTimeout(20, TimeUnit.MINUTES)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder().baseUrl(BASE_URL).client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val marketPlaceAPI: MarketPlaceAPI by lazy {
        retrofit.create(MarketPlaceAPI::class.java)
    }
}