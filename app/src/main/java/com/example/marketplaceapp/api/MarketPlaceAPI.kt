package com.example.marketplaceapp.api

import com.example.marketplaceapp.model.*
import com.example.marketplaceapp.utils.Constant
import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface MarketPlaceAPI {


    // User
    @POST(Constant.REGISTRATION)
    suspend fun registration(
        @Body registrationCredential: RegistrationCredential
    ): Response<RegistrationResponse>

    @POST(Constant.LOGIN)
    suspend fun login(
        @Body loginCredential: LoginCredential
    ): Response<LoginResponse>

    @POST(Constant.RESET_PASSWORD)
    suspend fun resetPassword(
        @Body resetPasswordCredential: ResetPasswordCredential
    ): Response<GeneralResponse>

    @GET(Constant.GET_USERINFO)
    suspend fun getUserInfo(
        @Header("username") username: String
    ): Response<UserInfoResponse>

    @POST(Constant.UPDATE_USERDATA)
    suspend fun updateUserData(
        @Header("token") token: String,
        @Body profileCredential: ProfileCredential
    ): Response<ProfileResponse>


    // Products
    @GET(Constant.GET_PRODUCTS)
    suspend fun getProducts(
        @Header("token") token: String,
        @Header("limit") limit: Int? = 20,
        @Header("filter") filter: String? = null,
        @Header("sort") sort: String? = null,
        @Header("skip") skip: Int? = null
    ): Response<ProductResponse>

    @GET(Constant.GET_PRODUCTS)
    suspend fun getProducts2(@HeaderMap headers: Map<String, String>): Response<ProductResponse>

    @Multipart
    @POST(Constant.ADD_PRODUCT)
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

    @POST(Constant.DELETE_PRODUCTS)
    suspend fun deleteProducts(
        @Header("token") token: String,
        @Query("product_id") productId: String
    ): Response<ProductDeleteResponse>

    @POST(Constant.UPDATE_PRODUCTS)
    suspend fun updateProducts(
        @Query("product_id") productId: String,
        @Header("token") token: String,
        @Body productUpdate: ProductUpdate
    ): Response<ProductUpdateResponse>


    // Orders
    @GET(Constant.GET_ORDERS)
    suspend fun getOrders(@HeaderMap headers: Map<String, String>): Response<OrdersResponse>

    @Multipart
    @POST(Constant.ADD_ORDER)
    suspend fun addOrder(
        @Header("token") token: String,
        @Part("title") title: String,
        @Part("description") description: String,
        @Part("price_per_unit") pricePerUnit: String,
        @Part("units") units: String,
        @Part("owner_username") ownerUsername: String
    ): Response<OrderAddResponse>

}