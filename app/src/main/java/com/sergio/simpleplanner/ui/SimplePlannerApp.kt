package com.sergio.simpleplanner.ui

import androidx.compose.runtime.Composable
import com.sergio.common.component.DefaultScaffold
import com.sergio.simpleplanner.navigation.AppState
import com.sergio.simpleplanner.navigation.DailyPlanNavHost
import com.sergio.simpleplanner.navigation.rememberAppState

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