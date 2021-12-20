package com.example.marketplaceapp.constants

import java.text.SimpleDateFormat
import java.util.*

object Constant {
    const val sharedPreferenceName = "com.example.marketplaceapp"

    fun getDate(timeStamp: Long): String? {
        val formatter = SimpleDateFormat("MMM dd, yyy", Locale.getDefault())
        return formatter.format(timeStamp)
    }
}