package com.example.bloom_app.ads


import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError

@Composable
fun BannerAdView(
    modifier: Modifier = Modifier
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(
                    context,
                    AdSize.FULL_WIDTH
                ))
                adUnitId = AdConfig.BANNER_AD_UNIT_ID

                adListener = object : AdListener() {
                    override fun onAdLoaded() {
                        AdAnalytics.bannerAdLoaded()
                        Log.d("BannerAd", "Banner ad loaded")
                    }

                    override fun onAdFailedToLoad(error: LoadAdError) {
                        AdAnalytics.bannerAdFailed(error.message)
                        Log.d("BannerAd", "Banner ad failed: ${error.message}")
                    }

                    override fun onAdImpression() {
                        AdAnalytics.bannerAdImpression()
                    }
                }

                loadAd(AdRequest.Builder().build())
            }
        }
    )
}