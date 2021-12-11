package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName


data class UserInfoResponse(
    val code : Int,
    val data : List<UserData>,
    val timestamp: Long
)

data class UserData(
    val username : String,

    @SerializedName("phone_number")
    val phoneNumber : Long,

    val email : String,

    @SerializedName("firebase_token")
    val firebaseToken: String,

    @SerializedName("is_activated")
    val isActivated: Boolean,

    @SerializedName("creation_time")
    val creationTime: String
)
