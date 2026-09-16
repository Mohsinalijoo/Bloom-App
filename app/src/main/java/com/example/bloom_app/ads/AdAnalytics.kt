package com.example.bloom_app.ads


import android.util.Log

/**
 * Basic ad event analytics tracker.
 * In production, this would integrate with Firebase Analytics or similar.
 */
object AdAnalytics {
    private const val TAG = "BloomAdAnalytics"

    fun logEvent(eventName: String, params: Map<String, String> = emptyMap()) {
        val paramString = if (params.isNotEmpty()) {
            params.entries.joinToString(", ") { "${it.key}=${it.value}" }
        } else ""

        Log.d(TAG, "Ad Event: $eventName ${if (paramString.isNotEmpty()) "[$paramString]" else ""}")
    }

    // App Open Ad Events
    fun appOpenAdRequested() = logEvent("app_open_ad_requested")
    fun appOpenAdLoaded() = logEvent("app_open_ad_loaded")
    fun appOpenAdFailed(error: String) = logEvent("app_open_ad_failed", mapOf("error" to error))
    fun appOpenAdShown() = logEvent("app_open_ad_shown")
    fun appOpenAdDismissed() = logEvent("app_open_ad_dismissed")

    // Banner Ad Events
    fun bannerAdLoaded() = logEvent("banner_ad_loaded")
    fun bannerAdFailed(error: String) = logEvent("banner_ad_failed", mapOf("error" to error))
    fun bannerAdImpression() = logEvent("banner_ad_impression")
}