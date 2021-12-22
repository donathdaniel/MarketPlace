package com.example.marketplaceapp.utils

import android.content.SharedPreferences
import com.example.marketplaceapp.BazaarSharedPreference

fun String.deleteQuotes() = this.replace("\"", "")

fun SharedPreferences.putToken(value: String) {
    BazaarSharedPreference.sharedPref.edit()?.putString(Constant.accessToken, value)?.apply()
}

fun SharedPreferences.putUsername(value: String) {
    BazaarSharedPreference.sharedPref.edit()?.putString(Constant.username, value)?.apply()
}

fun SharedPreferences.getToken(): String {
    return BazaarSharedPreference.sharedPref.getString(Constant.accessToken, "").toString()
}

fun SharedPreferences.getUsername(): String {
    return BazaarSharedPreference.sharedPref.getString(Constant.username, "").toString()
}