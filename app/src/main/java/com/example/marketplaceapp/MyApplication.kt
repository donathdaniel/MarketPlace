package com.example.marketplaceapp

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

object MyApplication : Application() {

    lateinit var sharedPref : SharedPreferences

    override fun onCreate() {
        super.onCreate()

        sharedPref = applicationContext.getSharedPreferences("com.example.marketplaceapp", Context.MODE_PRIVATE)
        MainActivity()
    }
}
