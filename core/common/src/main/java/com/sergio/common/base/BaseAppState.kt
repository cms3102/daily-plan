package com.sergio.common.base

import androidx.navigation.NavHostController

interface BaseAppState {
    val navController: NavHostController
    val showBackButton: Boolean
}