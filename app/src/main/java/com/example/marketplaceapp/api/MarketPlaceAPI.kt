package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface MarketPlaceAPI {


    // User
    @POST("user/register")
    suspend fun registration(
        @Body registrationCredential: RegistrationCredential
    ): Response<RegistrationResponse>

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
        @Header("username") username: String
    ): Response<UserInfoResponse>

    @POST("user/update")
    suspend fun updateUserData(
        @Header("token") token: String,
        @Body profileCredential: ProfileCredential
    ): Response<ProfileResponse>


    // Products
    @GET("products")
    suspend fun getProducts(
        @Header("token") token: String,
        @Header("limit") limit: Int? = 20,
        @Header("filter") filter: String? = null,
        @Header("sort") sort: String? = null,
        @Header("skip") skip: Int? = null
        //@Header("sort") sort : String? = "{\"creation_time\" : -1}"
    ): Response<ProductResponse>

    @Multipart
    @POST("products/add")
    suspend fun addProducts(
        @Header("token") token: String,
        @Part("title") title: String,
        @Part("description") description: String,
        @Part("price_per_unit") pricePerUnit: String,
        @Part("units") units: String,
        @Part("is_active") isActive: Boolean,
        @Part("rating") rating: Double? = null,
        @Part("amount_type") amountType: String,
        @Part("price_type") priceType: String
    ): Response<ProductAddResponse>

    @POST("products/remove")
    suspend fun deleteProducts(
        @Header("token") token: String,
        @Query("product_id") productId: String
    ): Response<ProductDeleteResponse>

}