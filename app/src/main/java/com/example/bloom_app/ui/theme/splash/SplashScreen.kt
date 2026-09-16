package com.example.bloom_app.ui.theme.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.theme.components.BloomFlower
import com.example.bloom_app.ui.theme.components.SmallDecorativeStar
import com.example.bloom_app.ui.theme.BloomBackground
import com.example.bloom_app.ui.theme.BloomPetalLight
import com.example.bloom_app.ui.theme.BloomPetalMedium
import com.example.bloom_app.ui.theme.BloomSecondary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    preferencesManager: PreferencesManager,
    onNavigateToOnboarding: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val isOnboardingCompleted by preferencesManager.isOnboardingCompleted.collectAsState(initial = null)

    LaunchedEffect(isOnboardingCompleted) {
        if (isOnboardingCompleted != null) {
            delay(2000)
            if (isOnboardingCompleted == true) {
                onNavigateToHome()
            } else {
                onNavigateToOnboarding()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground),
        contentAlignment = Alignment.Center
    ) {
        // Decorative stars
        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 80.dp, y = 200.dp),
            color = BloomPetalLight
        )

        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-60).dp, y = 280.dp),
            color = BloomSecondary
        )

        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .offset(x = 70.dp, y = (-250).dp),
            color = BloomPetalMedium
        )

        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-90).dp, y = (-300).dp),
            color = BloomPetalLight
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(40.dp)
        ) {
            BloomFlower(
                size = 180.dp,
                progress = 1f
            )
        }
    }
}