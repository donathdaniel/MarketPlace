package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import retrofit2.Response
import retrofit2.http.*

interface MarketPlaceAPI {


    // User
    @POST("user/register")
    suspend fun registration(
        @Body registrationCredential: RegistrationCredential
    ): Response<RegistrationResponse>

//    @Headers("Accept: text/html")
//    @GET("/user/activate")
//    suspend fun activation(
//        @Query("username") username : String
//    ) : Response<String>

    @POST("user/login")
    suspend fun login(
        @Body loginCredential: LoginCredential
    ): Response<LoginResponse>

    @POST("user/reset")
    suspend fun resetPassword(
        @Body resetPasswordCredential: ResetPasswordCredential
    ): Response<GeneralResponse>

    @GET("user/data")
    suspend fun getUserInfo(
        @Header("username") username : String
    ): Response<UserInfoResponse>



    // Products
    @GET("products")
    suspend fun getProducts(
        @Header("token") token: String,
        @Header("limit") limit: Int? = null,
        @Header("filter") filter: String? = null,
        @Header("sort") sort: String? = null,
        @Header("skip") skip: Int? = null
        //@Header("sort") sort : String? = "{\"creation_time\" : -1}"
    ): Response<ProductBase>

    @POST("products/add")
    suspend fun addProducts(
        @Header("token") token: String,
        @Body productAdd: ProductAdd
    ): Response<ProductAddResponse>


}