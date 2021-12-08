package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import retrofit2.Response

class MarketPlaceApiRepository {

    suspend fun register(registrationCredential : RegistrationCredential): Response<RegistrationResponse>{
        return MarketPlaceRetrofitInstance.marketPlaceAPI.registration(registrationCredential)
    }

    suspend fun activation(username : String): Response<String>{
        return MarketPlaceRetrofitInstance.marketPlaceAPI.activation(username)
    }

    suspend fun login(loginCredential : LoginCredential): Response<LoginResponse>{
        return MarketPlaceRetrofitInstance.marketPlaceAPI.login(loginCredential)
    }

    suspend fun getProducts(token : String): Response<ProductCredential>{
        return MarketPlaceRetrofitInstance.marketPlaceAPI.getProducts(token)
    }

}