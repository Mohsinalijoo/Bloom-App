package com.example.bloom_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.bloom_app.ads.AppOpenAdManager
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.navigation.BloomNavigation
import com.example.bloom_app.ui.theme.BloomBackground
import com.example.bloom_app.ui.theme.BloomTheme

class MainActivity : ComponentActivity() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var appOpenAdManager: AppOpenAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferencesManager = PreferencesManager(applicationContext)
        appOpenAdManager = AppOpenAdManager(application, preferencesManager)

        try {
            appOpenAdManager.init()
        } catch (e: Exception) {
            Log.e("MainActivity", "AppOpenAdManager init failed: ${e.message}")
        }

        setContent {
            BloomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = BloomBackground
                ) {
                    val navController = rememberNavController()
                    BloomNavigation(
                        navController = navController,
                        preferencesManager = preferencesManager,
                        appOpenAdManager = appOpenAdManager
                    )
                }
            }
        }
    }
}
