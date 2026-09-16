package com.example.bloom_app.ui.theme.onboarding

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.bloom_app.ads.AppOpenAdManager
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.theme.BloomBackground

@Composable
fun OnboardingScreen(
    preferencesManager: PreferencesManager,
    appOpenAdManager: AppOpenAdManager,
    onOnboardingComplete: () -> Unit
) {
    val viewModel = remember { OnboardingViewModel(preferencesManager) }
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BloomBackground)
    ) {
        AnimatedContent(
            targetState = state.currentStep,
            transitionSpec = {
                if (targetState > initialState) {
                    (slideInHorizontally { width -> width } + fadeIn())
                        .togetherWith(slideOutHorizontally { width -> -width } + fadeOut())
                } else {
                    (slideInHorizontally { width -> -width } + fadeIn())
                        .togetherWith(slideOutHorizontally { width -> width } + fadeOut())
                }
            },
            label = "onboarding_transition"
        ) { step ->
            when (step) {
                0 -> OnboardingStep1(
                    onGetStarted = { viewModel.nextStep() }
                )
                1 -> OnboardingStep2(
                    userName = state.userName,
                    onUserNameChanged = { viewModel.updateUserName(it) },
                    onContinue = { viewModel.nextStep() },
                    onBack = { viewModel.previousStep() },
                    currentStep = 1,
                    totalSteps = 4
                )
                2 -> OnboardingStep3(
                    trackingItems = state.trackingItems,
                    onToggleTracking = { viewModel.toggleTracking(it) },
                    onContinue = { viewModel.nextStep() },
                    onBack = { viewModel.previousStep() },
                    currentStep = 2,
                    totalSteps = 4
                )
                3 -> OnboardingStep4(
                    selectedTime = state.selectedReminderTime,
                    customTime = state.customTime,
                    onSelectTime = { viewModel.selectReminderTime(it) },
                    onCustomTimeChanged = { viewModel.updateCustomTime(it) },
                    onEnterBloom = {
                        viewModel.completeOnboarding {
                            appOpenAdManager.notifyOnboardingCompleted()
                            onOnboardingComplete()
                        }
                    },
                    onSkip = {
                        viewModel.skipReminder {
                            appOpenAdManager.notifyOnboardingCompleted()
                            onOnboardingComplete()
                        }
                    },
                    onBack = { viewModel.previousStep() },
                    currentStep = 3,
                    totalSteps = 4
                )
            }
        }
    }
}