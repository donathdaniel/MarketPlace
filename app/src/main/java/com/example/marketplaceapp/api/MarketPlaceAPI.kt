package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface MarketPlaceAPI {

    @POST("user/register")
    suspend fun registration(
        @Body registrationCredential : RegistrationCredential
    ): Response<RegistrationResponse>


    @Headers("Accept: text/html")
    @GET("/user/activate")
    suspend fun activation(
        @Query("username") username : String
    ) : Response<String>


    @POST("user/login")
    suspend fun login(
        @Body loginCredential : LoginCredential
    ): Response<LoginResponse>


    @GET("products")
    suspend fun getProducts(
        @Header("token") token : String,
        @Header("limit") limit : String? = null,
        @Header("filter") filter : String? = null,
        @Header("sort") sort : String? = null
    ): Response<ProductCredential>


}