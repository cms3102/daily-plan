package com.sergio.common.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.sergio.common.base.BaseAppState
import com.sergio.common.base.BaseDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    appState: BaseAppState,
    destination: BaseDestination?,
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = {
            destination?.appBarTitle?.run {
                Text(text = stringResource(id = this))
            }
        },
        navigationIcon = {
            if (!appState.showBackButton) {
                IconButton(onClick = { appState.navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}