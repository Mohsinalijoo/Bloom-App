package com.example.bloom_app.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bloom_app.data.BloomRepository
import com.example.bloom_app.ui.theme.*

@Composable
fun InsightsScreen() {
    val context = LocalContext.current
    val repository = remember { BloomRepository(context) }
    val log by repository.dailyLog.collectAsState(initial = com.example.bloom_app.data.DailyLog())

    val score = calculateScore(log)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            "Insights",
            style = MaterialTheme.typography.displaySmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic),
            color = BloomTextDark
        )
        Text("Your week at a glance, and what to try next", style = MaterialTheme.typography.bodyMedium, color = BloomTextLight)

        Spacer(Modifier.height(20.dp))

        Text("Your score", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Column {
                        Text("$score%", style = MaterialTheme.typography.displayMedium.copy(fontFamily = DMSerifDisplay, fontWeight = FontWeight.Normal), color = BloomTextDark)
                        Text("today's average", style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(onClick = {}, label = { Text("Week", fontSize = 12.sp) }, colors = AssistChipDefaults.assistChipColors(containerColor = BloomPrimary, labelColor = androidx.compose.ui.graphics.Color.White))
                        AssistChip(onClick = {}, label = { Text("Month", fontSize = 12.sp) })
                    }
                }
                Spacer(Modifier.height(16.dp))
                // Simple bar chart
                Row(modifier = Modifier.fillMaxWidth().height(120.dp), verticalAlignment = Alignment.Bottom, horizontalArrangement = Arrangement.SpaceEvenly) {
                    listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { i, day ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val h = ((score * (0.4f + (i % 3) * 0.2f)).coerceIn(20f, 100f)).dp
                            Box(
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(h)
                                    .background(BloomLightPink, RoundedCornerShape(topStart = 6.dp, topEnd = 6.dp))
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(day, style = MaterialTheme.typography.labelSmall, color = BloomTextLight)
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("Tap a day to see its summary", style = MaterialTheme.typography.bodySmall, color = BloomTextLight, modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Cycle", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Text("🩸", modifier = Modifier.padding(end = 12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (log.cycleLastPeriodDate != null) "Last period: ${log.cycleLastPeriodDate}" else "No cycle logged yet",
                        style = MaterialTheme.typography.titleSmall, color = BloomTextDark
                    )
                    Text(
                        text = if (log.cycleLastPeriodDate != null) "Bloom will estimate your next period" else "Log your last period date",
                        style = MaterialTheme.typography.bodySmall, color = BloomTextLight
                    )
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Daily summary", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$score%", style = MaterialTheme.typography.titleLarge.copy(fontFamily = DMSerifDisplay, fontWeight = FontWeight.Bold), color = BloomPrimary)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Today", style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
                        Text(
                            text = if (score == 0) "Nothing logged yet today — no rush, tap a goal whenever you're ready."
                            else "Great start! Keep tracking to see patterns.",
                            style = MaterialTheme.typography.bodySmall, color = BloomTextLight
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(20.dp))

        Text("Symptom patterns", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        listOf(
            "Bloating" to (log.symptoms.bloating?.label ?: "Not logged"),
            "Skin & acne" to (log.symptoms.skinAcne?.label ?: "Not logged"),
            "Mood" to (log.symptoms.mood?.label ?: "Not logged"),
            "Sleep quality" to (log.symptoms.sleepQuality?.label ?: "Not logged")
        ).forEach { (name, value) ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(name, style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
                    Text(value, style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

private fun calculateScore(log: com.example.bloom_app.data.DailyLog): Int {
    val meals = (log.meals.loggedCount / 3f).coerceAtMost(1f)
    val move = (log.movementMinutes / 30f).coerceAtMost(1f)
    val water = (log.waterGlasses / 8f).coerceAtMost(1f)
    val relax = (log.relaxationMinutes / 15f).coerceAtMost(1f)
    val sleep = (log.sleepHours / 8f).coerceAtMost(1f)
    return (((meals + move + water + relax + sleep) / 5f) * 100f).toInt()
}
