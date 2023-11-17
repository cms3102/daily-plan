package com.sergio.dailyplan.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.sergio.home.navigation.homeRoute
import com.sergio.home.navigation.homeScreen

@Composable
internal fun DailyPlanNavHost(
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