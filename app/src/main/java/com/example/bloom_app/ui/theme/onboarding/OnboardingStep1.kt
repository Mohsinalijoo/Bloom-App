package com.example.bloom_app.ui.theme.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ui.theme.components.BloomButton
import com.example.bloom_app.ui.theme.components.BloomFlower
import com.example.bloom_app.ui.theme.components.SmallDecorativeStar
import com.example.bloom_app.ui.theme.BloomBackground
import com.example.bloom_app.ui.theme.BloomPetalLight
import com.example.bloom_app.ui.theme.BloomSecondary
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun OnboardingStep1(
    onGetStarted: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
    ) {
        // Decorative stars
        SmallDecorativeStar(
            modifier = Modifier
                .offset(x = 60.dp, y = 120.dp),
            color = BloomPetalLight
        )

        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = (-40).dp, y = 200.dp),
            color = BloomSecondary
        )

        SmallDecorativeStar(
            modifier = Modifier
                .offset(x = 50.dp, y = 500.dp),
            color = BloomPetalLight
        )

        SmallDecorativeStar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .offset(x = (-30).dp, y = 50.dp),
            color = BloomSecondary
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp)
                .padding(bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(1f))

            BloomFlower(
                size = 160.dp,
                progress = 1f
            )

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Welcome to Bloom",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = DMSerifDisplay,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "A simple daily companion for managing PCOS — track your cycle, meals, movement, water, relaxation and sleep.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = BloomTextLight
            )

            Spacer(modifier = Modifier.weight(1f))

            BloomButton(
                text = "Get started",
                onClick = onGetStarted,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}