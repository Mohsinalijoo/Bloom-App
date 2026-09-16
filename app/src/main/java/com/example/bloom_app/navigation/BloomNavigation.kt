package com.example.bloom_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bloom_app.ads.AppOpenAdManager
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.theme.home.HomeScreen
import com.example.bloom_app.ui.theme.onboarding.OnboardingScreen
import com.example.bloom_app.ui.theme.splash.SplashScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Home : Screen("home")
}

@Composable
fun BloomNavigation(
    navController: NavHostController,
    preferencesManager: PreferencesManager,
    appOpenAdManager: AppOpenAdManager
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                preferencesManager = preferencesManager,
                onNavigateToOnboarding = {
                    navController.navigate(Screen.Onboarding.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Onboarding.route) {
            OnboardingScreen(
                preferencesManager = preferencesManager,
                appOpenAdManager = appOpenAdManager,
                onOnboardingComplete = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Onboarding.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(preferencesManager = preferencesManager)
        }
    }
}