package com.example.bloom_app.ads

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.bloom_app.data.PreferencesManager
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class AppOpenAdManager(
    private val application: Application,
    private val preferencesManager: PreferencesManager
) : DefaultLifecycleObserver, Application.ActivityLifecycleCallbacks {

    private val TAG = "AppOpenAdManager"
    private var appOpenAd: AppOpenAd? = null
    private var isLoadingAd = false
    private var isShowingAd = false
    private var loadTime: Long = 0
    private var currentActivity: Activity? = null
    private var isFirstLaunch = true
    private var hasHandledColdStart = false
    private val mainHandler = Handler(Looper.getMainLooper())

    fun init() {
        try {
            application.registerActivityLifecycleCallbacks(this)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val onboardingCompleted = withContext(Dispatchers.IO) {
                        preferencesManager.isOnboardingCompleted.first()
                    }
                    isFirstLaunch = !onboardingCompleted
                    if (!isFirstLaunch) loadAd()
                } catch (e: Exception) {
                    Log.e(TAG, "init check failed: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "init failed: ${e.message}")
        }
    }

    private fun loadAd() {
        if (isLoadingAd || isAdAvailable()) return
        mainHandler.post {
            try {
                isLoadingAd = true
                AdAnalytics.appOpenAdRequested()
                AppOpenAd.load(
                    application, AdConfig.APP_OPEN_AD_UNIT_ID, AdRequest.Builder().build(),
                    object : AppOpenAd.AppOpenAdLoadCallback() {
                        override fun onAdLoaded(ad: AppOpenAd) {
                            appOpenAd = ad
                            isLoadingAd = false
                            loadTime = Date().time
                            AdAnalytics.appOpenAdLoaded()
                            Log.d(TAG, "Ad loaded")
                            if (!hasHandledColdStart && !isFirstLaunch) {
                                hasHandledColdStart = true
                                currentActivity?.let { showAdIfAvailable(it) }
                            }
                        }
                        override fun onAdFailedToLoad(err: LoadAdError) {
                            isLoadingAd = false
                            AdAnalytics.appOpenAdFailed(err.message)
                            Log.d(TAG, "Ad failed: ${err.message}")
                        }
                    }
                )
            } catch (e: Exception) {
                isLoadingAd = false
                Log.e(TAG, "loadAd failed: ${e.message}")
            }
        }
    }

    private fun isAdAvailable() = appOpenAd != null && (Date().time - loadTime) < AdConfig.APP_OPEN_AD_EXPIRY_MS

    fun showAdIfAvailable(activity: Activity, onAdDismissed: () -> Unit = {}) {
        if (isFirstLaunch || isShowingAd) { onAdDismissed(); return }
        if (!isAdAvailable()) { onAdDismissed(); loadAd(); return }
        mainHandler.post {
            try {
                appOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        appOpenAd = null; isShowingAd = false
                        AdAnalytics.appOpenAdDismissed()
                        onAdDismissed(); loadAd()
                    }
                    override fun onAdFailedToShowFullScreenContent(err: AdError) {
                        appOpenAd = null; isShowingAd = false
                        AdAnalytics.appOpenAdFailed(err.message)
                        onAdDismissed(); loadAd()
                    }
                    override fun onAdShowedFullScreenContent() { AdAnalytics.appOpenAdShown() }
                }
                isShowingAd = true
                appOpenAd?.show(activity)
            } catch (e: Exception) {
                isShowingAd = false
                Log.e(TAG, "show failed: ${e.message}")
                onAdDismissed()
            }
        }
    }

    fun notifyOnboardingCompleted() {
        isFirstLaunch = false
        hasHandledColdStart = true  // Don't interrupt right after onboarding
        loadAd()
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        if (isFirstLaunch || !hasHandledColdStart) return
        currentActivity?.let { showAdIfAvailable(it) }
    }

    override fun onActivityCreated(a: Activity, s: Bundle?) {}
    override fun onActivityStarted(a: Activity) { if (!isShowingAd) currentActivity = a }
    override fun onActivityResumed(a: Activity) { currentActivity = a }
    override fun onActivityPaused(a: Activity) {}
    override fun onActivityStopped(a: Activity) {}
    override fun onActivitySaveInstanceState(a: Activity, o: Bundle) {}
    override fun onActivityDestroyed(a: Activity) { if (currentActivity == a) currentActivity = null }
}