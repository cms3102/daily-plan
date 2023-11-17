package com.sergio.simpleplanner.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.sergio.home.navigation.homeRoute
import com.sergio.home.navigation.homeScreen

@Composable
internal fun SimplePlannerNavHost(
    appState: AppState,
    paddingValues: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = homeRoute
    ) {
        homeScreen()
    }
}