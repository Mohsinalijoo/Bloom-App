package com.example.bloom_app.ui.theme.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom_app.ui.theme.components.BloomFlower
import com.example.bloom_app.ui.theme.BloomCardBackground
import com.example.bloom_app.ui.theme.BloomLightPink
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun HomeHeader(
    greeting: String,
    userName: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = greeting,
                style = MaterialTheme.typography.labelMedium,
                color = BloomTextLight,
                letterSpacing = 1.sp
            )
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = DMSerifDisplay,
                    fontStyle = FontStyle.Italic
                ),
                color = BloomTextDark
            )
        }

        // Avatar placeholder
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(BloomLightPink),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (userName.isNotEmpty()) userName.first().uppercase() else "S",
                style = MaterialTheme.typography.titleMedium,
                color = BloomPrimary
            )
        }
    }
}

@Composable
fun GoalsCard(
    date: String,
    goalsCompleted: Int,
    totalGoals: Int,
    onStartClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = BloomCardBackground
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Today's Goals",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontFamily = DMSerifDisplay,
                        fontStyle = FontStyle.Italic
                    ),
                    color = BloomTextDark
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall,
                    color = BloomTextLight
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Let's get blooming",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontStyle = FontStyle.Italic
                    ),
                    color = BloomTextDark
                )

                Text(
                    text = "$goalsCompleted of $totalGoals complete",
                    style = MaterialTheme.typography.bodySmall,
                    color = BloomTextLight
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Start here button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(BloomPrimary)
                        .clickable { onStartClick() }
                        .padding(horizontal = 24.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "Start here",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.White
                    )
                }
            }

            // Flower visualization
            BloomFlower(
                size = 100.dp,
                progress = if (totalGoals > 0) goalsCompleted.toFloat() / totalGoals.toFloat() else 0f
            )
        }
    }
}