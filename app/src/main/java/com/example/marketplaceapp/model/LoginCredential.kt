package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName

data class LoginCredential (
    val username : String,
    val password : String
)

data class LoginResponse (
    val username : String,
    val email : String,

    @SerializedName("phone_number")
    val phoneNumber : Long,

//    @SerializedName("image_path")
//    val imagePath: String,

    val token: String,
    @SerializedName("creation_time")
    val creationTime: Long,

    @SerializedName("refresh_time")
    val refreshTime: Long
)

data class ResetPasswordCredential(
    val username : String,
    val email : String
)