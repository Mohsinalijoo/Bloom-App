package com.example.bloom_app.ui.theme.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.bloom_app.data.*
import com.example.bloom_app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun BloomDialogContainer(
    title: String,
    subtitle: String? = null,
    onDismiss: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = BloomSurface)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                // Handle bar
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 40.dp, height = 4.dp)
                            .clip(RoundedCornerShape(2.dp))
                            .background(BloomDivider)
                    )
                }

                Row(
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontFamily = DMSerifDisplay,
                                fontStyle = FontStyle.Italic
                            ),
                            color = BloomTextDark
                        )
                        subtitle?.let {
                            Spacer(Modifier.height(4.dp))
                            Text(it, style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
                        }
                    }
                    IconButton(onClick = onDismiss, modifier = Modifier.size(32.dp)) {
                        Icon(Icons.Default.Close, contentDescription = "Close", tint = BloomTextLight)
                    }
                }

                Spacer(Modifier.height(16.dp))
                content()
            }
        }
    }
}

@Composable
fun BloomPill(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(if (selected) BloomPrimary else BloomLightPink.copy(alpha = 0.4f))
            .border(1.dp, if (selected) BloomPrimary else BloomDivider, RoundedCornerShape(20.dp))
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
            color = if (selected) Color.White else BloomTextDark
        )
    }
}

@Composable
fun StepperControl(
    value: Int,
    unit: String,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        RoundStepBtn(icon = Icons.Default.Remove, onClick = onDecrement)
        Spacer(Modifier.width(24.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.displayMedium.copy(fontFamily = DMSerifDisplay),
                color = BloomTextDark
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = unit,
                style = MaterialTheme.typography.bodyMedium,
                color = BloomTextLight,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Spacer(Modifier.width(24.dp))
        RoundStepBtn(icon = Icons.Default.Add, onClick = onIncrement)
    }
}

@Composable
private fun RoundStepBtn(icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(CircleShape)
            .background(BloomLightPink.copy(alpha = 0.5f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(icon, contentDescription = null, tint = BloomPrimary)
    }
}

// ============ LOG MEALS DIALOG ============
@Composable
fun LogMealsDialog(current: MealsLog, onSelect: (String, MealChoice) -> Unit, onDismiss: () -> Unit) {
    BloomDialogContainer(
        title = "Log Meals",
        subtitle = "How would you describe each meal today?",
        onDismiss = onDismiss
    ) {
        MealSlot("Breakfast", "🥞", current.breakfast) { onSelect("breakfast", it) }
        Spacer(Modifier.height(16.dp))
        MealSlot("Lunch", "🥗", current.lunch) { onSelect("lunch", it) }
        Spacer(Modifier.height(16.dp))
        MealSlot("Dinner", "🍽️", current.dinner) { onSelect("dinner", it) }
        Spacer(Modifier.height(16.dp))
        val logged = current.loggedCount
        Text(
            text = "$logged of 3 meals logged today",
            style = MaterialTheme.typography.bodySmall,
            color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}

@Composable
private fun MealSlot(label: String, icon: String, selected: MealChoice?, onSelect: (MealChoice) -> Unit) {
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$icon $label", style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
        }
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BloomPill("Light", selected == MealChoice.LIGHT, { onSelect(MealChoice.LIGHT) }, Modifier.weight(1f))
            BloomPill("Balanced", selected == MealChoice.BALANCED, { onSelect(MealChoice.BALANCED) }, Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BloomPill("Indulgent", selected == MealChoice.INDULGENT, { onSelect(MealChoice.INDULGENT) }, Modifier.weight(1f))
            BloomPill("Skipped", selected == MealChoice.SKIPPED, { onSelect(MealChoice.SKIPPED) }, Modifier.weight(1f))
        }
    }
}

// ============ STEPPER DIALOGS (Movement, Water, Relaxation, Sleep) ============
@Composable
fun LogMovementDialog(current: Int, target: Int, onSave: (Int) -> Unit, onDismiss: () -> Unit) {
    var value by remember { mutableIntStateOf(current) }
    BloomDialogContainer(title = "Log Movement", onDismiss = onDismiss) {
        Text("Today's movement", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        StepperControl(value, "min", { value = (value - 5).coerceAtLeast(0) }, { value += 5 })
        Spacer(Modifier.height(12.dp))
        Text("Goal: $target min", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        InfoTip("💪 Get moving today",
            "Try a brisk 30-min walk, a swim, or some light strength training — these are especially good for insulin sensitivity with PCOS.")
        Spacer(Modifier.height(16.dp))
        BloomFilledButton(text = "Save (+${value - current} min)", onClick = { onSave(value); onDismiss() })
    }
}

@Composable
fun LogWaterDialog(current: Int, target: Int, onSave: (Int) -> Unit, onDismiss: () -> Unit) {
    var value by remember { mutableIntStateOf(current) }
    BloomDialogContainer(title = "Log Water", onDismiss = onDismiss) {
        Text("Today's water", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        StepperControl(value, "glasses", { value = (value - 1).coerceAtLeast(0) }, { value += 1 })
        Spacer(Modifier.height(12.dp))
        Text("Goal: $target glasses", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        InfoTip("💧 Start hydrating",
            "Try starting with a glass first thing in the morning.")
        Spacer(Modifier.height(16.dp))
        BloomFilledButton(text = "Save", onClick = { onSave(value); onDismiss() })
    }
}

@Composable
fun LogRelaxationDialog(current: Int, target: Int, onSave: (Int) -> Unit, onDismiss: () -> Unit) {
    var value by remember { mutableIntStateOf(current) }
    BloomDialogContainer(title = "Log Relaxation", onDismiss = onDismiss) {
        Text("Today's relaxation", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        StepperControl(value, "min", { value = (value - 5).coerceAtLeast(0) }, { value += 5 })
        Spacer(Modifier.height(12.dp))
        Text("Goal: $target min", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        InfoTip("🧘‍♀️ Make time to unwind",
            "Even 5–10 minutes of deep breathing, meditation, or gentle yoga can help lower stress hormones.")
        Spacer(Modifier.height(16.dp))
        BloomFilledButton(text = "Save", onClick = { onSave(value); onDismiss() })
    }
}

@Composable
fun LogSleepDialog(current: Int, target: Int, onSave: (Int) -> Unit, onDismiss: () -> Unit) {
    var value by remember { mutableIntStateOf(current) }
    BloomDialogContainer(title = "Log Sleep", onDismiss = onDismiss) {
        Text("Today's sleep", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(12.dp))
        StepperControl(value, "hrs", { value = (value - 1).coerceAtLeast(0) }, { value += 1 })
        Spacer(Modifier.height(12.dp))
        Text("Goal: $target hrs", style = MaterialTheme.typography.bodySmall, color = BloomTextLight,
            modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        Spacer(Modifier.height(20.dp))
        InfoTip("😴 Log last night's sleep",
            "Consistent, close-to-8-hr nights can help keep your hormones balanced.")
        Spacer(Modifier.height(16.dp))
        BloomFilledButton(text = "Save", onClick = { onSave(value); onDismiss() })
    }
}

@Composable
fun LogCycleDialog(currentDate: String?, onSave: (String) -> Unit, onDismiss: () -> Unit) {
    val today = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(Date())
    var selected by remember { mutableStateOf(currentDate ?: today) }
    BloomDialogContainer(title = "Log Cycle", subtitle = "When did your last period start?", onDismiss = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = BloomLightPink.copy(alpha = 0.3f)),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("📅 Selected date", style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
                Spacer(Modifier.height(8.dp))
                Text(selected, style = MaterialTheme.typography.headlineSmall.copy(fontFamily = DMSerifDisplay), color = BloomPrimary)
                Spacer(Modifier.height(8.dp))
                Text("Tap Done to log today as your last period start date. Bloom will estimate your next period.",
                    style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
            }
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BloomPill("Today", selected == today, { selected = today }, Modifier.weight(1f))
            BloomPill("Yesterday", false, {
                val cal = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
                selected = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault()).format(cal.time)
            }, Modifier.weight(1f))
        }
        Spacer(Modifier.height(20.dp))
        BloomFilledButton(text = "Done", onClick = { onSave(selected); onDismiss() })
    }
}

@Composable
fun TodaysSymptomsDialog(
    current: SymptomsLog,
    onBloating: (SymptomLevel) -> Unit,
    onSkin: (SymptomLevel) -> Unit,
    onMood: (MoodLevel) -> Unit,
    onSleep: (SleepQualityLevel) -> Unit,
    onDismiss: () -> Unit
) {
    BloomDialogContainer(
        title = "Today's symptoms",
        subtitle = "These are separate from your daily goals — they help you spot patterns over time.",
        onDismiss = onDismiss
    ) {
        SymptomRow("💧 Bloating", listOf("None", "Mild", "Moderate", "Severe"), current.bloating?.label) {
            onBloating(SymptomLevel.valueOf(it.uppercase().replace(" ", "_").replace("-", "_")))
        }
        Spacer(Modifier.height(16.dp))
        SymptomRow("✨ Skin & acne", listOf("Clear", "Mild", "Moderate", "Flare-up"), current.skinAcne?.label) {
            onSkin(SymptomLevel.valueOf(it.uppercase().replace(" ", "_").replace("-", "_")))
        }
        Spacer(Modifier.height(16.dp))
        SymptomRow("😊 Mood", listOf("Great", "Okay", "Low", "Very low"), current.mood?.label) {
            onMood(MoodLevel.valueOf(it.uppercase().replace(" ", "_")))
        }
        Spacer(Modifier.height(16.dp))
        SymptomRow("🌙 Sleep quality", listOf("Great", "Okay", "Poor", "Very poor"), current.sleepQuality?.label) {
            onSleep(SleepQualityLevel.valueOf(it.uppercase().replace(" ", "_")))
        }
        Spacer(Modifier.height(20.dp))
        BloomFilledButton(text = "Done", onClick = onDismiss)
    }
}

@Composable
private fun SymptomRow(title: String, options: List<String>, selected: String?, onSelect: (String) -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.titleSmall, color = BloomTextDark)
        Spacer(Modifier.height(8.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp), modifier = Modifier.fillMaxWidth()) {
            options.forEach { opt ->
                BloomPill(opt, selected == opt, { onSelect(opt) }, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun InfoTip(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BloomLightPink.copy(alpha = 0.3f)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold), color = BloomTextDark)
            Spacer(Modifier.height(4.dp))
            Text(body, style = MaterialTheme.typography.bodySmall, color = BloomTextLight)
        }
    }
}

@Composable
private fun BloomFilledButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(26.dp),
        colors = ButtonDefaults.buttonColors(containerColor = BloomPrimary)
    ) {
        Text(text, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
    }
}