package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.LoginCredential
import com.example.marketplaceapp.model.LoginResponse
import com.example.marketplaceapp.model.RegistrationCredential
import com.example.marketplaceapp.model.RegistrationResponse
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

}