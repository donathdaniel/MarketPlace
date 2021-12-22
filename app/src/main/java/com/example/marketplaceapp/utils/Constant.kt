package com.example.marketplaceapp.utils

import java.text.SimpleDateFormat
import java.util.*

object Constant {

    const val sharedPreferenceName = "com.example.marketplaceapp"
    const val marketPlaceAPIBaseUrl = "https://pure-gorge-51703.herokuapp.com/"

    const val accessToken: String = "accessToken"
    const val username: String = "username"

//    Bundle
    const val units: String = "units"
    const val isActive: String = "isActive"
    const val amountType: String = "amountType"
    const val priceType: String ="priceType"
    const val pricePerUnit: String = "pricePerUnit"
    const val title: String ="title"

//    Endpoints


    const val loginSuccessfulMessage: String = "Login successful"
    const val loginUnsuccessfulMessage: String = "Login unsuccessful"

    const val registerSuccessful: String = "You need to activate your account"
    const val registerUnsuccessfulMessage: String = "Registration failed"

    const val updateUserInfoMessage: String = "Your data is updated"
    const val noTimelineItemMessage: String = "No Data Found"


    fun getDate(timeStamp: Long): String? {
        val formatter = SimpleDateFormat("MMM dd, yyy", Locale.getDefault())
        return formatter.format(timeStamp)
    }
}