package com.example.bloom_app.ui.theme.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun OnboardingStep4(
    selectedTime: String,
    customTime: String,
    onSelectTime: (String) -> Unit,
    onCustomTimeChanged: (String) -> Unit,
    onEnterBloom: () -> Unit,
    onSkip: () -> Unit,
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            // Clock icon
            Text(
                text = "🕐",
                fontSize = 40.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "When should we remind you?",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontFamily = DMSerifDisplay,
                    fontStyle = FontStyle.Italic
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "We'll send one daily nudge to log how you're doing.",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = BloomTextLight
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Pick a time of day",
                style = MaterialTheme.typography.titleSmall,
                color = BloomTextDark,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Time options
            ReminderTimeOption(
                title = "Morning",
                subtitle = "7:30 AM — start the day on track",
                isSelected = selectedTime == "morning",
                onClick = { onSelectTime("morning") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReminderTimeOption(
                title = "Afternoon",
                subtitle = "1:00 PM — a midday check-in",
                isSelected = selectedTime == "afternoon",
                onClick = { onSelectTime("afternoon") }
            )

            Spacer(modifier = Modifier.height(12.dp))

            ReminderTimeOption(
                title = "Evening",
                subtitle = "8:00 PM — wind down and reflect",
                isSelected = selectedTime == "evening",
                onClick = { onSelectTime("evening") }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Or set a custom time",
                style = MaterialTheme.typography.titleSmall,
                color = BloomTextDark,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Custom time input
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = BloomDivider,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = customTime,
                    style = MaterialTheme.typography.bodyLarge,
                    color = BloomTextDark
                )
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Set time",
                    tint = BloomTextLight,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }

        // Bottom buttons
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BloomButton(
                text = "Enter Bloom",
                onClick = onEnterBloom,
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(
                onClick = onSkip,
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                Text(
                    text = "I'll set this up later",
                    style = MaterialTheme.typography.bodyMedium,
                    color = BloomTextLight
                )
            }
        }
    }
}

@Composable
private fun ReminderTimeOption(
    title: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isSelected) BloomPrimary else BloomDivider,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Radio button
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            androidx.compose.foundation.layout.Box(
                modifier = Modifier
                    .size(20.dp)
                    .border(
                        width = 2.dp,
                        color = if (isSelected) BloomPrimary else BloomDivider,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (isSelected) {
                    androidx.compose.foundation.layout.Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(BloomPrimary)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = BloomTextDark
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = BloomTextLight
            )
        }
    }
}