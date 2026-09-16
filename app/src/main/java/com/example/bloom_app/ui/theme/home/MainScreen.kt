package com.example.bloom_app.ui.theme.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.bloom_app.data.PreferencesManager
import com.example.bloom_app.ui.home.components.BottomNavigationBar
import com.example.bloom_app.ui.theme.screens.InsightsScreen
import com.example.bloom_app.ui.theme.screens.LearnScreen
import com.example.bloom_app.ui.theme.screens.SettingsScreen

@Composable
fun MainScreen(preferencesManager: PreferencesManager) {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { paddingValues ->
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedIndex) {
                0 -> HomeScreenContent(preferencesManager = preferencesManager)
                1 -> InsightsScreen()
                2 -> LearnScreen()
                3 -> SettingsScreen(preferencesManager = preferencesManager)
            }
        }
    }
}