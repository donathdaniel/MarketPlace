package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName

data class ProductCredential (

    @SerializedName("item_count")
    val itemCount : Int,
    val products : List<Product>
)

data class Product(
    val rating : Double,

    @SerializedName("amount_type")
    val amountType : String,

    @SerializedName("price_type")
    val priceType : String,

    @SerializedName("product_id")
    val productId : String,

    val username : String,

    @SerializedName("is_active")
    val isActive : Boolean,

    @SerializedName("price_per_unit")
    val pricePerUnit : String,

    val units : String,
    val description : String,
    val title : String,

    @SerializedName("creation_time")
    val creationTime : Long

)