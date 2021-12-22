package com.example.marketplaceapp

import android.app.Application

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        BazaarSharedPreference.initializeSharedPreference(context = applicationContext)
    }
}
