package com.sergio.common.base

import androidx.compose.ui.graphics.vector.ImageVector

interface BaseDestination {
    val selectedIcon: ImageVector
    val unselectedIcon: ImageVector
    val menuTitle: Int
    val appBarTitle: Int
    val route: String
}