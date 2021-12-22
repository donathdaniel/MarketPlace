package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName
import retrofit2.SkipCallbackExecutor
import java.io.File

data class ProductResponse(

    @SerializedName("item_count")
    val itemCount: Int,
    val products: List<Product>
)

data class Product(
    val rating: Double,

    @SerializedName("amount_type")
    val amountType: String,

    @SerializedName("price_type")
    val priceType: String,

    @SerializedName("product_id")
    val productId: String,

    val username: String,

    @SerializedName("is_active")
    val isActive: Boolean,

    @SerializedName("price_per_unit")
    val pricePerUnit: String,

    val units: String,
    val description: String,
    val title: String,

    @SerializedName("creation_time")
    val creationTime: Long
)

data class ProductAdd(
    val uploadImages: File? = null,
    val title: String,
    val description: String,

    @SerializedName("price_per_unit")
    val pricePerUnit: String,

    val units: String,

    @SerializedName("is_active")
    val isActive: Boolean,

    val rating: Double? = null,

    @SerializedName("amount_type")
    val amountType: String,

    @SerializedName("price_type")
    val priceType: String,
)

data class ProductAddResponse(
    val creation : String,

    @SerializedName("product_id")
    val productId : String
)

data class ProductDeleteResponse(
    val message : String,

    @SerializedName("product_id")
    val productId : String,

    @SerializedName("deletion_time")
    val deletionTime : String
)