package com.sergio.dailyplan.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.sergio.common.component.DefaultScaffold
import com.sergio.dailyplan.navigation.AppState
import com.sergio.dailyplan.navigation.DailyPlanNavHost
import com.sergio.dailyplan.navigation.rememberAppState

@Composable
internal fun DailyPlanApp(
    appState: AppState = rememberAppState()
) {
    DefaultScaffold(appState = appState) { paddingValues ->
        DailyPlanNavHost(
            appState = appState,
            paddingValues = paddingValues
        )
    }
}