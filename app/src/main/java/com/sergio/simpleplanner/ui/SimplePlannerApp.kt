package com.sergio.simpleplanner.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import com.sergio.common.component.DefaultScaffold
import com.sergio.simpleplanner.navigation.AppState
import com.sergio.simpleplanner.navigation.SimplePlannerNavHost
import com.sergio.simpleplanner.navigation.rememberAppState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SimplePlannerApp(
    appState: AppState = rememberAppState()
) {
    DefaultScaffold(
        navController = appState.navController,
        showBackButton = appState.showBackButton
    ) { insets ->
        SimplePlannerNavHost(
            appState = appState,
            insets = insets
        )
    }
}