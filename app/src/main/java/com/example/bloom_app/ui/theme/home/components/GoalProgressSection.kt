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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom_app.ui.theme.home.GoalProgress
import com.example.bloom_app.ui.theme.BloomCardBackground
import com.example.bloom_app.ui.theme.BloomDivider
import com.example.bloom_app.ui.theme.BloomPrimary
import com.example.bloom_app.ui.theme.BloomTextDark
import com.example.bloom_app.ui.theme.BloomTextLight
import com.example.bloom_app.ui.theme.DMSerifDisplay

@Composable
fun GoalProgressSection(
    goalProgressList: List<GoalProgress>,
    onGoalClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Goal Progress",
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
                containerColor = BloomCardBackground
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                goalProgressList.forEachIndexed { index, goal ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onGoalClick(goal.id) }
                            .padding(vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(goal.color.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = goal.icon,
                                fontSize = 18.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = goal.title,
                                style = MaterialTheme.typography.titleSmall,
                                color = BloomTextDark
                            )
                            Text(
                                text = goal.progress,
                                style = MaterialTheme.typography.bodySmall,
                                color = BloomTextLight
                            )
                        }

                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = BloomPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    if (index < goalProgressList.lastIndex) {
                        Divider(
                            color = BloomDivider.copy(alpha = 0.5f),
                            thickness = 0.5.dp
                        )
                    }
                }
            }
        }
    }
}