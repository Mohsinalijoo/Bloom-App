package com.example.bloom_app.ui.theme.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ads.BannerAdView
import com.example.bloom_app.data.BloomRepository
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.theme.dialogs.*
import com.example.bloom_app.ui.theme.home.components.*
import com.example.bloom_app.ui.theme.BloomBackground

enum class LogDialogType { NONE, MEALS, MOVEMENT, WATER, RELAX, SLEEP, CYCLE, SYMPTOMS }

@Composable
fun HomeScreen(preferencesManager: PreferencesManager) {
    MainScreen(preferencesManager)
}

@Composable
fun HomeScreenContent(preferencesManager: PreferencesManager) {
    val context = LocalContext.current
    val repository = remember { BloomRepository(context) }
    val viewModel = remember { HomeViewModel(preferencesManager, repository) }
    val state by viewModel.state.collectAsState()
    val userName by preferencesManager.userName.collectAsState(initial = "Sofia")

    var activeDialog by remember { mutableStateOf(LogDialogType.NONE) }

    LaunchedEffect(userName) { viewModel.updateUserName(userName) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
            .verticalScroll(rememberScrollState())
    ) {
        HomeHeader(greeting = state.greeting, userName = state.userName)

        GoalsCard(
            date = state.currentDate,
            goalsCompleted = state.goalsCompleted,
            totalGoals = state.totalGoals,
            onStartClick = { activeDialog = LogDialogType.MEALS },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(20.dp))

        PcosFaqCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(20.dp))

        QuickActionsSection(
            onCycleClick = { activeDialog = LogDialogType.CYCLE },
            onSymptomsClick = { activeDialog = LogDialogType.SYMPTOMS },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(20.dp))

        GoalProgressSection(
            goalProgressList = state.goalProgressList,
            onGoalClick = { goalId ->
                activeDialog = when (goalId) {
                    "meals" -> LogDialogType.MEALS
                    "movement" -> LogDialogType.MOVEMENT
                    "water" -> LogDialogType.WATER
                    "relaxation" -> LogDialogType.RELAX
                    "sleep" -> LogDialogType.SLEEP
                    else -> LogDialogType.NONE
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(20.dp))

        TodaysTipCard(
            onMarkDone = { activeDialog = LogDialogType.SLEEP },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))

        BannerAdView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )

        Spacer(Modifier.height(16.dp))
    }

    when (activeDialog) {
        LogDialogType.MEALS -> LogMealsDialog(
            current = state.dailyLog.meals,
            onSelect = { slot, choice -> viewModel.setMeal(slot, choice) },
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.MOVEMENT -> LogMovementDialog(
            current = state.dailyLog.movementMinutes,
            target = state.targets.movementMinutes,
            onSave = viewModel::saveMovement,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.WATER -> LogWaterDialog(
            current = state.dailyLog.waterGlasses,
            target = state.targets.waterGlasses,
            onSave = viewModel::saveWater,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.RELAX -> LogRelaxationDialog(
            current = state.dailyLog.relaxationMinutes,
            target = state.targets.relaxationMinutes,
            onSave = viewModel::saveRelax,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.SLEEP -> LogSleepDialog(
            current = state.dailyLog.sleepHours,
            target = state.targets.sleepHours,
            onSave = viewModel::saveSleep,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.CYCLE -> LogCycleDialog(
            currentDate = state.dailyLog.cycleLastPeriodDate,
            onSave = viewModel::saveCycle,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.SYMPTOMS -> TodaysSymptomsDialog(
            current = state.dailyLog.symptoms,
            onBloating = viewModel::setBloating,
            onSkin = viewModel::setSkin,
            onMood = viewModel::setMood,
            onSleep = viewModel::setSleepQuality,
            onDismiss = { activeDialog = LogDialogType.NONE }
        )
        LogDialogType.NONE -> {}
    }
}
