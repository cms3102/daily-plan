package com.sergio.common.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.sergio.common.base.BaseDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
    navController: NavController,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    snackbarHostState: SnackbarHostState? = null,
    destination: BaseDestination? = null,
    topAppBarColors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    val showTopAppBar = destination != null
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = if (showTopAppBar) {
            modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        } else {
            modifier
        },
        topBar = topBar ?: {
            if (showTopAppBar) {
                DefaultTopAppBar(
                    navController = navController,
                    showBackButton = showBackButton,
                    destination = destination,
                    scrollBehavior = topAppBarScrollBehavior,
                    colors = topAppBarColors
                )
            }
        },
        bottomBar = bottomBar,
        snackbarHost = {
            snackbarHostState?.let { SnackbarHost(hostState = it) }
        },
        content = {
            content(it)
        }
    )
}