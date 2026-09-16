package com.example.bloom_app.ui.theme.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ui.theme.components.BloomButton
import com.example.bloom_app.ui.theme.components.OnboardingProgressIndicator
import com.example.bloom_app.ui.theme.BloomBackground
import com.example.bloom_app.ui.theme.BloomDivider
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun OnboardingStep2(
    userName: String,
    onUserNameChanged: (String) -> Unit,
    onContinue: () -> Unit,
    onBack: () -> Unit,
    currentStep: Int,
    totalSteps: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .padding(horizontal = 24.dp)
    ) {
        // Top bar with back button and progress indicator
        Row(
            modifier = Modifier
                .fillMaxWidth()
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

        Spacer(modifier = Modifier.height(60.dp))

        // Title
        Text(
            text = "A little about you",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = DMSerifDisplay,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Name input field
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = BloomDivider,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            if (userName.isEmpty()) {
                Text(
                    text = "Your first name",
                    style = MaterialTheme.typography.bodyLarge,
                    color = BloomTextLight
                )
            }
            BasicTextField(
                value = userName,
                onValueChange = onUserNameChanged,
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = BloomTextDark
                ),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        BloomButton(
            text = "Continue",
            onClick = onContinue,
            enabled = userName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 40.dp)
        )
    }
}