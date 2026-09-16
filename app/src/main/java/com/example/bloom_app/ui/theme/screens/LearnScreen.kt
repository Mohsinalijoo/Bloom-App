package com.example.bloom_app.ui.theme.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.bloom_app.data.PcosQuestions
import com.example.bloom_app.ui.theme.*

@Composable
fun LearnScreen() {
    val grouped = PcosQuestions.all.groupBy { it.category }
    var expandedIndex by remember { mutableStateOf<Int?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            "Learn",
            style = MaterialTheme.typography.displaySmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic),
            color = BloomTextDark
        )
        Text("Understand PCOS, at your own pace", style = MaterialTheme.typography.bodyMedium, color = BloomTextLight)

        Spacer(Modifier.height(20.dp))

        var qIndex = 0
        grouped.forEach { (category, questions) ->
            Text(
                text = category,
                style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic),
                color = BloomTextDark,
                modifier = Modifier.padding(vertical = 8.dp)
            )

            questions.forEach { q ->
                val myIndex = qIndex++
                val isExpanded = expandedIndex == myIndex
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { expandedIndex = if (isExpanded) null else myIndex },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                q.question,
                                style = MaterialTheme.typography.titleSmall,
                                color = BloomTextDark,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                                tint = BloomPrimary
                            )
                        }
                        AnimatedVisibility(visible = isExpanded) {
                            Column {
                                Spacer(Modifier.height(12.dp))
                                Divider(color = BloomDivider.copy(alpha = 0.5f))
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    q.answer,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = BloomTextDark
                                )
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}