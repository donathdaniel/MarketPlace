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

}