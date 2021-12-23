package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName

data class OrdersResponse(
    @SerializedName("item_count")
    val itemCount: Int,
    val orders: List<Order>,
    val timestamp: Long
)

data class Order(
    @SerializedName("order_id")
    val order_id: String,

    val username: String,
    val status: String,

    @SerializedName("owner_username")
    val ownerUsername: String,

    @SerializedName("price_per_unit")
    val pricePerUnit: String,

    val units: String,
    val description: String,
    val title: String,

    @SerializedName("creation_time")
    val creationTime: Long
)

data class OrderAdd(
    val title: String,
    val description: String,

    @SerializedName("price_per_unit")
    val pricePerUnit: String,

    val units: String,

    @SerializedName("owner_username")
    val ownerUsername: String,
)

data class OrderAddResponse(
    val creation: String,

    @SerializedName("order_id")
    val orderId: String,
    val username: String,
    val status: String,

    @SerializedName("owner_username")
    val ownerUsername: String,

    @SerializedName("price_per_unit")
    val pricePerUnit: String,
    val units: String,
    val description: String,
    val title: String,

    @SerializedName("creation_time")
    val creationTime: Long
)