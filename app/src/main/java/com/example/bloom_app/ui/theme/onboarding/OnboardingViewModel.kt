package com.example.bloom_app.ui.theme.onboarding


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom_app.data.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class TrackingItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val icon: String,
    val isEnabled: Boolean = true
)

data class OnboardingState(
    val currentStep: Int = 0,
    val userName: String = "",
    val trackingItems: List<TrackingItem> = listOf(
        TrackingItem("meals", "Meals · 3 balanced meals", "Steadier blood sugar helps keep PCOS symptoms in check.", "🍽️"),
        TrackingItem("movement", "Movement · 30 minutes", "Supports insulin sensitivity, a key lever in PCOS.", "🏃‍♀️"),
        TrackingItem("water", "Water · 8 glasses", "Helps with energy and bloating.", "💧"),
        TrackingItem("relaxation", "Relaxation · 15 minutes", "Lower stress means lower cortisol, which can worsen symptoms.", "🧘‍♀️"),
        TrackingItem("sleep", "Sleep · 8 hours", "Consistent sleep helps balance hormones over time.", "😴"),
        TrackingItem("cycle", "Cycle · period dates", "Spot your personal pattern and predict your next period.", "🔴"),
        TrackingItem("symptoms", "Symptom check-in · bloating, skin, mood, sleep quality", "Track how you feel day-to-day to notice patterns over time.", "📋")
    ),
    val selectedReminderTime: String = "",
    val customTime: String = "08:00 AM"
)

class OnboardingViewModel(
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun updateUserName(name: String) {
        _state.value = _state.value.copy(userName = name)
    }

    fun toggleTracking(id: String) {
        val updatedItems = _state.value.trackingItems.map {
            if (it.id == id) it.copy(isEnabled = !it.isEnabled) else it
        }
        _state.value = _state.value.copy(trackingItems = updatedItems)
    }

    fun selectReminderTime(time: String) {
        _state.value = _state.value.copy(selectedReminderTime = time)
    }

    fun updateCustomTime(time: String) {
        _state.value = _state.value.copy(customTime = time)
    }

    fun nextStep() {
        val current = _state.value.currentStep
        if (current < 3) {
            _state.value = _state.value.copy(currentStep = current + 1)
        }
    }

    fun previousStep() {
        val current = _state.value.currentStep
        if (current > 0) {
            _state.value = _state.value.copy(currentStep = current - 1)
        }
    }

    fun completeOnboarding(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _state.value
            if (state.userName.isNotBlank()) {
                preferencesManager.setUserName(state.userName)
            }

            val items = state.trackingItems
            preferencesManager.setTrackingPreferences(
                meals = items.find { it.id == "meals" }?.isEnabled ?: true,
                movement = items.find { it.id == "movement" }?.isEnabled ?: true,
                water = items.find { it.id == "water" }?.isEnabled ?: true,
                relaxation = items.find { it.id == "relaxation" }?.isEnabled ?: true,
                sleep = items.find { it.id == "sleep" }?.isEnabled ?: true,
                cycle = items.find { it.id == "cycle" }?.isEnabled ?: true,
                symptoms = items.find { it.id == "symptoms" }?.isEnabled ?: true
            )

            if (state.selectedReminderTime.isNotBlank()) {
                preferencesManager.setReminderTime(state.selectedReminderTime)
            } else if (state.customTime.isNotBlank()) {
                preferencesManager.setReminderTime(state.customTime)
            }

            preferencesManager.setOnboardingCompleted(true)
            onComplete()
        }
    }

    fun skipReminder(onComplete: () -> Unit) {
        viewModelScope.launch {
            val state = _state.value
            if (state.userName.isNotBlank()) {
                preferencesManager.setUserName(state.userName)
            }
            preferencesManager.setOnboardingCompleted(true)
            onComplete()
        }
    }
}