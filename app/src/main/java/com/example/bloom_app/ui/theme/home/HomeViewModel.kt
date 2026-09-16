package com.example.bloom_app.ui.theme.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloom_app.data.*
import com.example.bloom_app.ui.theme.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

data class GoalProgress(
    val id: String,
    val title: String,
    val icon: String,
    val progress: String,
    val color: androidx.compose.ui.graphics.Color,
    val fraction: Float
)

data class HomeState(
    val userName: String = "Sofia",
    val currentDate: String = "",
    val greeting: String = "",
    val goalsCompleted: Int = 0,
    val totalGoals: Int = 5,
    val goalProgressList: List<GoalProgress> = emptyList(),
    val dailyLog: DailyLog = DailyLog(),
    val targets: GoalTargets = GoalTargets()
)

class HomeViewModel(
    private val preferencesManager: PreferencesManager,
    private val repository: BloomRepository
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        val cal = Calendar.getInstance()
        val date = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
        val greeting = when (cal.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> "GOOD MORNING,"
            in 12..16 -> "GOOD AFTERNOON,"
            else -> "GOOD EVENING,"
        }
        _state.value = _state.value.copy(currentDate = date, greeting = greeting)

        viewModelScope.launch {
            repository.dailyLog.collect { log ->
                val t = _state.value.targets
                val list = listOf(
                    GoalProgress("meals", "Meals", "🍽️",
                        "${log.meals.loggedCount}/${t.mealsTarget} meals", BloomOrange,
                        (log.meals.loggedCount.toFloat() / t.mealsTarget).coerceIn(0f, 1f)),
                    GoalProgress("movement", "Movement", "🏃‍♀️",
                        "${log.movementMinutes}/${t.movementMinutes} min", BloomGreen,
                        (log.movementMinutes.toFloat() / t.movementMinutes).coerceIn(0f, 1f)),
                    GoalProgress("water", "Water", "💧",
                        "${log.waterGlasses}/${t.waterGlasses} glasses", BloomBlue,
                        (log.waterGlasses.toFloat() / t.waterGlasses).coerceIn(0f, 1f)),
                    GoalProgress("relaxation", "Relaxation", "🧘‍♀️",
                        "${log.relaxationMinutes}/${t.relaxationMinutes} min", BloomPurple,
                        (log.relaxationMinutes.toFloat() / t.relaxationMinutes).coerceIn(0f, 1f)),
                    GoalProgress("sleep", "Sleep", "😴",
                        "${log.sleepHours}/${t.sleepHours} hrs", BloomTeal,
                        (log.sleepHours.toFloat() / t.sleepHours).coerceIn(0f, 1f))
                )
                val completed = list.count { it.fraction >= 1f }
                _state.value = _state.value.copy(
                    dailyLog = log,
                    goalProgressList = list,
                    goalsCompleted = completed
                )
            }
        }
    }

    fun updateUserName(name: String) { _state.value = _state.value.copy(userName = name) }

    fun setMeal(slot: String, choice: MealChoice) = viewModelScope.launch { repository.setMeal(slot, choice) }
    fun saveMovement(min: Int) = viewModelScope.launch { repository.setMovement(min) }
    fun saveWater(g: Int) = viewModelScope.launch { repository.setWater(g) }
    fun saveRelax(min: Int) = viewModelScope.launch { repository.setRelax(min) }
    fun saveSleep(h: Int) = viewModelScope.launch { repository.setSleep(h) }
    fun saveCycle(d: String) = viewModelScope.launch { repository.setCycleDate(d) }
    fun setBloating(l: SymptomLevel) = viewModelScope.launch { repository.setBloating(l) }
    fun setSkin(l: SymptomLevel) = viewModelScope.launch { repository.setSkin(l) }
    fun setMood(l: MoodLevel) = viewModelScope.launch { repository.setMood(l) }
    fun setSleepQuality(l: SleepQualityLevel) = viewModelScope.launch { repository.setSleepQuality(l) }
}