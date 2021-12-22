package com.example.marketplaceapp

import android.content.Context
import android.content.SharedPreferences
import com.example.marketplaceapp.utils.Constant

object BazaarSharedPreference {

    lateinit var sharedPref: SharedPreferences

    fun initializeSharedPreference(context: Context) {
        sharedPref =
            context.getSharedPreferences(Constant.sharedPreferenceName, Context.MODE_PRIVATE)
    }

    fun SharedPreferences.putToken(value: String) {
        sharedPref.edit()?.putString(Constant.accessToken, value)?.apply()
    }

    fun SharedPreferences.putUsername(value: String) {
        sharedPref.edit()?.putString(Constant.username, value)?.apply()
    }

    fun SharedPreferences.getToken(): String {
        return sharedPref.getString(Constant.accessToken, "").toString()
    }

    fun SharedPreferences.getUsername(): String {
        return sharedPref.getString(Constant.username, "").toString()
    }

//    fun putString(key: String, value: String) {
//        sharedPref.edit()?.putString(key, value)?.apply()
//    }
//
//    fun getString(key: String, value: String): String {
//        return sharedPref.getString(key, value).toString()
//    }

}