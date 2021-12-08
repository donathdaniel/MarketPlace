package com.example.marketplaceapp.model

import com.google.gson.annotations.SerializedName
import java.io.File

data class RegistrationCredential (
    val username : String,
    val password : String,
    val email : String,

    @SerializedName("phone_number")
    val phoneNumber : Long,

    @SerializedName("firebase_token")
    val token: String = String(),

    //@SerializedName("userImage")
    //val userImage: File,
)

data class RegistrationResponse(
    val code : Int,
    val message : String,

    @SerializedName("creation_time")
    val creationTime: Long,
)