package com.sergio.simpleplanner.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.sergio.common.base.BaseAppState
import com.sergio.common.base.BaseDestination
import com.sergio.home.navigation.Home
import com.sergio.home.navigation.navigateToHome

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState {
    return remember { AppState(navController = navController) }
}

class AppState(
    override val navController: NavHostController
) : BaseAppState {
    override val showBackButton: Boolean
        get() = showBackButton()

    private fun showBackButton(): Boolean {
        return when (navController.currentDestination?.findMainDestination()) {
            is Home -> false
            else -> true
        }
    }

    internal fun NavController.navigateToTab(destination: BaseDestination) {
        when (destination) {
            is Home -> navigateToHome()
        }
    }

    private fun NavDestination?.findMainDestination(): BaseDestination? {
        return mainDestinations.find { destination ->
            this?.route?.contains(destination.route, true) ?: false
        }
    }

}

private val mainDestinations = listOf(Home)
