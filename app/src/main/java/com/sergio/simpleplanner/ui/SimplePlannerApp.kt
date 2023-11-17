package com.sergio.simpleplanner.ui

import androidx.compose.runtime.Composable
import com.sergio.common.component.DefaultScaffold
import com.sergio.simpleplanner.navigation.AppState
import com.sergio.simpleplanner.navigation.SimplePlannerNavHost
import com.sergio.simpleplanner.navigation.rememberAppState

@Composable
internal fun SimplePlannerApp(
    appState: AppState = rememberAppState()
) {
    DefaultScaffold(appState = appState) { paddingValues ->
        SimplePlannerNavHost(
            appState = appState,
            paddingValues = paddingValues
        )
    }
}