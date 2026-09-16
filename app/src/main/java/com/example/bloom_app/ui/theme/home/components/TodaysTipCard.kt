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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom_app.ui.theme.BloomGreen
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun TodaysTipCard(
    onMarkDone: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Today's tip",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontFamily = DMSerifDisplay,
                fontStyle = FontStyle.Italic
            ),
            color = BloomTextDark
        )

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = BloomPrimary.copy(alpha = 0.12f)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "LOG LAST NIGHT'S SLEEP",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    ),
                    color = BloomPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You haven't logged sleep yet. Consistent, close-to-8-hr nights can help keep your hormones more balanced — avoiding phones and screens before bed makes it easier to fall asleep.",
                    style = MaterialTheme.typography.bodySmall,
                    color = BloomTextDark
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextButton(onClick = { }) {
                        Text(
                            text = "Remind me later",
                            style = MaterialTheme.typography.labelMedium,
                            color = BloomTextLight
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(BloomGreen.copy(alpha = 0.9f))
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .clickable { onMarkDone() }
                    ) {
                        Text(
                            text = "✓ Mark as done",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White

                        )
                    }
                }
            }
        }
    }
}
