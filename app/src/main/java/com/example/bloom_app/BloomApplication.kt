package com.example.bloom_app

import android.app.Application
import android.util.Log
import com.google.android.gms.ads.MobileAds

class BloomApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        try {
            MobileAds.initialize(this) {}
        } catch (e: Exception) {
            Log.e("BloomApp", "AdMob init error: ${e.message}")
        }
    }
}