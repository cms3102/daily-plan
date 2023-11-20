package com.challenge.detail.navigation

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.challenge.detail.R
import com.challenge.detail.ui.DetailScreen
import com.challenge.model.Task
import com.google.gson.Gson
import com.sergio.common.base.BaseDestination

const val detailRoute = "detail_route"
const val detailArg = "detailArg"

object Detail : BaseDestination {
    override val appBarTitle: Int
        get() = R.string.detail_app_bar_title
    override val route: String
        get() = detailRoute
}

fun NavController.navigateToDetail(data: Task) {
    val json = Gson().toJson(data)
    val param = Uri.encode(json)
    navigate("$detailRoute/$param")
}

fun NavGraphBuilder.detailScreen(navController: NavController) {
    composable(
        route = "$detailRoute/{$detailArg}",
        arguments = listOf(
            navArgument(detailArg) {
                type = NavType.StringType
            }
        )
    ) { entry ->
        val param = entry.arguments?.getString(detailArg)
        val data = Gson().fromJson(param, Task::class.java)
        DetailScreen(
            navController = navController,
            task = data
        )
    }
}