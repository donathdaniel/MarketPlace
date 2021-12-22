package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class ProfileCredential(
    @SerializedName("phone_number")
    val phoneNumber: Long,

    val email: String,
    val username: String,

//    @SerializedName("userImage")
//    val userImage: File,
)

data class ProfileResponse(
    val code: Int,
    val updatedData: UpdatedUserData,
    val timestamp: Long
)

data class UpdatedUserData(
    val username: String,

    @SerializedName("phone_number")
    val phoneNumber: Long,

    val email: String,

    @SerializedName("firebase_token")
    val firebaseToken: String,

    @SerializedName("is_activated")
    val isActivated: Boolean,

    @SerializedName("creation_time")
    val creationTime: String,

    @SerializedName("token")
    val token: String
)