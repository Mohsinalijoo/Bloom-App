package com.example.bloom_app.ui.theme.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom_app.ui.theme.components.BloomButton
import com.example.bloom_app.ui.theme.components.OnboardingProgressIndicator
import com.example.bloom_app.ui.theme.BloomBackground
import com.example.bloom_app.ui.theme.BloomDivider
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.BloomToggleTrackOff
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun OnboardingStep3(
    trackingItems: List<TrackingItem>,
    onToggleTracking: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    currentStep: Int,
    totalSteps: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
    ) {
        // Top bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Back",
                    tint = BloomTextDark
                )
            }

            OnboardingProgressIndicator(
                totalSteps = totalSteps,
                currentStep = currentStep,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            )
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Star icon
            Text(
                text = "⭐",
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "What would you like to track daily?",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = DMSerifDisplay,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Pick the goals that matter to you — only these will show up on your dashboard. Partial progress still counts, and your flower fills in as you go.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = BloomTextLight,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Tracking items
            trackingItems.forEachIndexed { index, item ->
                TrackingItemRow(
                    item = item,
                    onToggle = { onToggleTracking(item.id) }
                )
                if (index < trackingItems.lastIndex) {
                    Divider(
                        color = BloomDivider.copy(alpha = 0.5f),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Targets and what you track can be adjusted any time in Settings.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = BloomTextLight,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        BloomButton(
            text = "Continue",
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp)
        )
    }
}

@Composable
private fun TrackingItemRow(
    item: TrackingItem,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = item.icon,
            fontSize = 24.sp
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleSmall,
                color = BloomTextDark
            )
            Text(
                text = item.subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = BloomTextLight
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Switch(
            checked = item.isEnabled,
            onCheckedChange = { onToggle() },
            colors = SwitchDefaults.colors(
                checkedThumbColor = androidx.compose.ui.graphics.Color.White,
                checkedTrackColor = BloomPrimary,
                uncheckedThumbColor = androidx.compose.ui.graphics.Color.White,
                uncheckedTrackColor = BloomToggleTrackOff,
                uncheckedBorderColor = BloomToggleTrackOff
            )
        )
    }
}