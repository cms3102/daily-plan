package com.sergio.common.component

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavController
import com.sergio.common.base.BaseAppState
import com.sergio.common.base.BaseDestination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScaffold(
    modifier: Modifier = Modifier,
    appState: BaseAppState,
    snackbarHostState: SnackbarHostState? = null,
    destination: BaseDestination? = null,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val showTopAppBar = destination != null

    Scaffold(
        modifier = if (showTopAppBar) {
            modifier.nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
        } else {
            modifier
        },
        topBar = topBar ?: {
            if (showTopAppBar) {
                DefaultTopAppBar(appState, destination, topAppBarScrollBehavior)
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