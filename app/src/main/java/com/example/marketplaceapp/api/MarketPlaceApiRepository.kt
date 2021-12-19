package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import retrofit2.Response

class MarketPlaceApiRepository {


    // Login
    suspend fun register(registrationCredential: RegistrationCredential): Response<RegistrationResponse> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.registration(registrationCredential)
    }

//    suspend fun activation(username : String): Response<String>{
//        return MarketPlaceRetrofitInstance.marketPlaceAPI.activation(username)
//    }

    suspend fun login(loginCredential: LoginCredential): Response<LoginResponse> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.login(loginCredential)
    }

    suspend fun resetPassword(resetPasswordCredential: ResetPasswordCredential): Response<GeneralResponse> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.resetPassword(resetPasswordCredential)
    }

    suspend fun getUserInfo(username: String): Response<UserInfoResponse> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.getUserInfo(username)
    }


    //Products
    suspend fun getProducts(
        token: String,
        limit: Int? = null,
        filter: String? = null,
        sort: String? = null,
        skip: Int? = null
    ): Response<ProductBase> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.getProducts(
            token,
            limit,
            filter,
            sort,
            skip
        )
    }

    suspend fun addProducts(
        token: String, productAdd: ProductAdd
    ): Response<ProductAddResponse> {
        return MarketPlaceRetrofitInstance.marketPlaceAPI.addProducts(token, productAdd)
    }

}