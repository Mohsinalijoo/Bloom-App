package com.example.bloom_app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "bloom_preferences")

class PreferencesManager(private val context: Context) {

    companion object {
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val USER_NAME = stringPreferencesKey("user_name")
        val TRACK_MEALS = booleanPreferencesKey("track_meals")
        val TRACK_MOVEMENT = booleanPreferencesKey("track_movement")
        val TRACK_WATER = booleanPreferencesKey("track_water")
        val TRACK_RELAXATION = booleanPreferencesKey("track_relaxation")
        val TRACK_SLEEP = booleanPreferencesKey("track_sleep")
        val TRACK_CYCLE = booleanPreferencesKey("track_cycle")
        val TRACK_SYMPTOMS = booleanPreferencesKey("track_symptoms")
        val REMINDER_TIME = stringPreferencesKey("reminder_time")
    }

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[ONBOARDING_COMPLETED] ?: false
    }

    val userName: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[USER_NAME] ?: "Sofia"
    }

    val trackMeals: Flow<Boolean> = context.dataStore.data.map { it[TRACK_MEALS] ?: true }
    val trackMovement: Flow<Boolean> = context.dataStore.data.map { it[TRACK_MOVEMENT] ?: true }
    val trackWater: Flow<Boolean> = context.dataStore.data.map { it[TRACK_WATER] ?: true }
    val trackRelaxation: Flow<Boolean> = context.dataStore.data.map { it[TRACK_RELAXATION] ?: true }
    val trackSleep: Flow<Boolean> = context.dataStore.data.map { it[TRACK_SLEEP] ?: true }
    val trackCycle: Flow<Boolean> = context.dataStore.data.map { it[TRACK_CYCLE] ?: true }
    val trackSymptoms: Flow<Boolean> = context.dataStore.data.map { it[TRACK_SYMPTOMS] ?: true }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun setUserName(name: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_NAME] = name
        }
    }

    suspend fun setTrackingPreferences(
        meals: Boolean,
        movement: Boolean,
        water: Boolean,
        relaxation: Boolean,
        sleep: Boolean,
        cycle: Boolean,
        symptoms: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[TRACK_MEALS] = meals
            preferences[TRACK_MOVEMENT] = movement
            preferences[TRACK_WATER] = water
            preferences[TRACK_RELAXATION] = relaxation
            preferences[TRACK_SLEEP] = sleep
            preferences[TRACK_CYCLE] = cycle
            preferences[TRACK_SYMPTOMS] = symptoms
        }
    }

    suspend fun setReminderTime(time: String) {
        context.dataStore.edit { preferences ->
            preferences[REMINDER_TIME] = time
        }
    }
}