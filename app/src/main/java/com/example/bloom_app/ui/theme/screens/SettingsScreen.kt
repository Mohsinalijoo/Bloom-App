package com.example.bloom_app.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(preferencesManager: PreferencesManager) {
    val userName by preferencesManager.userName.collectAsState(initial = "Sofia")
    val scope = rememberCoroutineScope()

    val meals by preferencesManager.trackMeals.collectAsState(initial = true)
    val movement by preferencesManager.trackMovement.collectAsState(initial = true)
    val water by preferencesManager.trackWater.collectAsState(initial = true)
    val relaxation by preferencesManager.trackRelaxation.collectAsState(initial = true)
    val sleep by preferencesManager.trackSleep.collectAsState(initial = true)
    val cycle by preferencesManager.trackCycle.collectAsState(initial = true)
    val symptoms by preferencesManager.trackSymptoms.collectAsState(initial = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Text(
            "Settings",
            style = MaterialTheme.typography.displaySmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic),
            color = BloomTextDark
        )
        Text("Your profile and daily goal targets", style = MaterialTheme.typography.bodyMedium, color = BloomTextLight)

        Spacer(Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(48.dp).clip(CircleShape).background(BloomLightPink),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (userName.isNotEmpty()) userName.first().uppercase() else "S", color = BloomPrimary, style = MaterialTheme.typography.titleMedium)
                    }
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(userName, style = MaterialTheme.typography.titleMedium, color = BloomTextDark)
                        Text("PCOS journey · Not set", style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
                    }
                }
                Divider(color = BloomDivider.copy(alpha = 0.5f))
                Row(
                    modifier = Modifier.fillMaxWidth().clickable {}.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Edit profile", style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, null, tint = BloomPrimary)
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("What you're tracking", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column {
                TrackToggle("🍽️", "Meals", meals) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(v, movement, water, relaxation, sleep, cycle, symptoms) }
                }
                TrackToggle("🏃‍♀️", "Movement", movement) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, v, water, relaxation, sleep, cycle, symptoms) }
                }
                TrackToggle("💧", "Water", water) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, movement, v, relaxation, sleep, cycle, symptoms) }
                }
                TrackToggle("🧘‍♀️", "Relaxation", relaxation) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, movement, water, v, sleep, cycle, symptoms) }
                }
                TrackToggle("😴", "Sleep", sleep) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, movement, water, relaxation, v, cycle, symptoms) }
                }
                TrackToggle("🩸", "Cycle", cycle) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, movement, water, relaxation, sleep, v, symptoms) }
                }
                TrackToggle("📋", "Symptom check-in", symptoms) { v ->
                    scope.launch { preferencesManager.setTrackingPreferences(meals, movement, water, relaxation, sleep, cycle, v) }
                }
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Daily goal targets", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column {
                TargetRow("🍽️", "Meals", "3 meals")
                TargetRow("🏃‍♀️", "Movement", "30 min")
                TargetRow("💧", "Water", "8 glasses")
                TargetRow("🧘‍♀️", "Relaxation", "15 min")
                TargetRow("😴", "Sleep", "8 hrs")
            }
        }

        Spacer(Modifier.height(24.dp))
        Text("Notifications", style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay, fontStyle = FontStyle.Italic), color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = BloomCardBackground),
            elevation = CardDefaults.cardElevation(0.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Reminder time · 8:00 AM", style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
                Spacer(Modifier.height(4.dp))
                Text("App version 1.0", style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
            }
        }

        Spacer(Modifier.height(32.dp))
    }
}

@Composable
private fun TrackToggle(icon: String, label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, modifier = Modifier.padding(end = 12.dp))
        Text(label, style = MaterialTheme.typography.titleSmall, color = BloomTextDark, modifier = Modifier.weight(1f))
        Switch(
            checked = checked,
            onCheckedChange = onChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = BloomPrimary,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = BloomToggleTrackOff
            )
        )
    }
}

@Composable
private fun TargetRow(icon: String, label: String, target: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(icon, modifier = Modifier.padding(end = 12.dp))
        Text(label, style = MaterialTheme.typography.titleSmall, color = BloomTextDark, modifier = Modifier.weight(1f))
        Text(target, style = MaterialTheme.typography.bodyMedium, color = BloomPrimary)
    }
}