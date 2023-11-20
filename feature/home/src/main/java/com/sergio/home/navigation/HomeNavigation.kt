package com.sergio.home.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.sergio.common.base.BaseDestination
import com.sergio.home.R
import com.sergio.home.ui.HomeScreen

const val homeRoute = "home_route"

object Home : BaseDestination {
    override val appBarTitle: Int
        get() = R.string.home_app_bar_title
    override val route: String
        get() = homeRoute
}

fun NavController.navigateToHome() {
    navigate(homeRoute)
}

fun NavGraphBuilder.homeScreen(navController: NavController) {
    composable(homeRoute) {
        HomeScreen(navController)
    }
}