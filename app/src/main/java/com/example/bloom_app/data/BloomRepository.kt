package com.example.bloom_app.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BloomRepository(private val context: Context) {

    companion object {
        val BREAKFAST = stringPreferencesKey("meal_breakfast")
        val LUNCH = stringPreferencesKey("meal_lunch")
        val DINNER = stringPreferencesKey("meal_dinner")
        val MOVEMENT_MIN = intPreferencesKey("movement_min")
        val WATER_GLASSES = intPreferencesKey("water_glasses")
        val RELAX_MIN = intPreferencesKey("relaxation_min")
        val SLEEP_HRS = intPreferencesKey("sleep_hrs")
        val CYCLE_DATE = stringPreferencesKey("cycle_last_period")
        val SYM_BLOATING = stringPreferencesKey("sym_bloating")
        val SYM_SKIN = stringPreferencesKey("sym_skin")
        val SYM_MOOD = stringPreferencesKey("sym_mood")
        val SYM_SLEEP = stringPreferencesKey("sym_sleep_q")
    }

    val dailyLog: Flow<DailyLog> = context.dataStore.data.map { prefs ->
        DailyLog(
            meals = MealsLog(
                breakfast = prefs[BREAKFAST]?.let { runCatching { MealChoice.valueOf(it) }.getOrNull() },
                lunch = prefs[LUNCH]?.let { runCatching { MealChoice.valueOf(it) }.getOrNull() },
                dinner = prefs[DINNER]?.let { runCatching { MealChoice.valueOf(it) }.getOrNull() }
            ),
            movementMinutes = prefs[MOVEMENT_MIN] ?: 0,
            waterGlasses = prefs[WATER_GLASSES] ?: 0,
            relaxationMinutes = prefs[RELAX_MIN] ?: 0,
            sleepHours = prefs[SLEEP_HRS] ?: 0,
            cycleLastPeriodDate = prefs[CYCLE_DATE],
            symptoms = SymptomsLog(
                bloating = prefs[SYM_BLOATING]?.let { runCatching { SymptomLevel.valueOf(it) }.getOrNull() },
                skinAcne = prefs[SYM_SKIN]?.let { runCatching { SymptomLevel.valueOf(it) }.getOrNull() },
                mood = prefs[SYM_MOOD]?.let { runCatching { MoodLevel.valueOf(it) }.getOrNull() },
                sleepQuality = prefs[SYM_SLEEP]?.let { runCatching { SleepQualityLevel.valueOf(it) }.getOrNull() }
            )
        )
    }

    suspend fun setMeal(slot: String, choice: MealChoice) {
        context.dataStore.edit { prefs ->
            when (slot) {
                "breakfast" -> prefs[BREAKFAST] = choice.name
                "lunch" -> prefs[LUNCH] = choice.name
                "dinner" -> prefs[DINNER] = choice.name
            }
        }
    }

    suspend fun addMovement(min: Int) = context.dataStore.edit { it[MOVEMENT_MIN] = (it[MOVEMENT_MIN] ?: 0) + min }
    suspend fun setMovement(min: Int) = context.dataStore.edit { it[MOVEMENT_MIN] = min.coerceAtLeast(0) }
    suspend fun addWater(glasses: Int) = context.dataStore.edit { it[WATER_GLASSES] = ((it[WATER_GLASSES] ?: 0) + glasses).coerceAtLeast(0) }
    suspend fun setWater(g: Int) = context.dataStore.edit { it[WATER_GLASSES] = g.coerceAtLeast(0) }
    suspend fun addRelax(min: Int) = context.dataStore.edit { it[RELAX_MIN] = ((it[RELAX_MIN] ?: 0) + min).coerceAtLeast(0) }
    suspend fun setRelax(min: Int) = context.dataStore.edit { it[RELAX_MIN] = min.coerceAtLeast(0) }
    suspend fun addSleep(hrs: Int) = context.dataStore.edit { it[SLEEP_HRS] = ((it[SLEEP_HRS] ?: 0) + hrs).coerceAtLeast(0) }
    suspend fun setSleep(h: Int) = context.dataStore.edit { it[SLEEP_HRS] = h.coerceAtLeast(0) }
    suspend fun setCycleDate(d: String) = context.dataStore.edit { it[CYCLE_DATE] = d }
    suspend fun setBloating(l: SymptomLevel) = context.dataStore.edit { it[SYM_BLOATING] = l.name }
    suspend fun setSkin(l: SymptomLevel) = context.dataStore.edit { it[SYM_SKIN] = l.name }
    suspend fun setMood(l: MoodLevel) = context.dataStore.edit { it[SYM_MOOD] = l.name }
    suspend fun setSleepQuality(l: SleepQualityLevel) = context.dataStore.edit { it[SYM_SLEEP] = l.name }
}