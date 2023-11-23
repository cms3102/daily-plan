package com.sergio.simpleplanner.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.challenge.detail.navigation.detailRoute
import com.challenge.detail.navigation.detailScreen
import com.sergio.common.theme.getColorScheme
import com.sergio.common.theme.setStatusBarColor
import com.sergio.home.navigation.homeRoute
import com.sergio.home.navigation.homeScreen

@Composable
internal fun SimplePlannerNavHost(
    appState: AppState,
    insets: PaddingValues
) {
    NavHost(
        navController = appState.navController,
        startDestination = homeRoute
    ) {
        appState.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            destination.route?.let { currentRoute ->
                when {
                    currentRoute.contains(detailRoute) -> setStatusBarColor(getColorScheme().primary)
                    else -> setStatusBarColor(getColorScheme().background)
                }
            }
        }
        homeScreen(appState.navController)
        detailScreen(appState.navController)
    }
}