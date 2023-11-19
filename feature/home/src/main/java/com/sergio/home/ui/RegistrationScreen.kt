package com.sergio.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sergio.common.theme.ComponentSizeRules
import com.sergio.common.theme.TextSizeRules
import com.sergio.home.R
import com.sergio.home.state.HomeIntent
import com.sergio.home.state.HomeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RegistrationScreen(
    state: ModalBottomSheetState,
    viewModel: HomeViewModel,
) {
    var titleState by remember { mutableStateOf("") }
    var titleErrorState by remember { mutableStateOf(false) }
    var descriptionState by remember { mutableStateOf("") }
    var descriptionErrorState by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    if (state.currentValue == ModalBottomSheetValue.Hidden) {
        titleState = ""
        descriptionState = ""
        focusManager.clearFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.onBackground)

            )
            Divider(modifier = Modifier.padding(vertical = 20.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = stringResource(id = R.string.request_task_title))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = titleState,
                    onValueChange = { titleState = it },
                    label = { Text(text = stringResource(id = R.string.task_title)) },
                    singleLine = true,
                    isError = titleErrorState
                )
                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = stringResource(id = R.string.request_task_description)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = descriptionState,
                    onValueChange = { descriptionState = it },
                    label = { Text(text = stringResource(id = R.string.task_description)) },
                    singleLine = true,
                    isError = descriptionErrorState,
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp, bottom = 10.dp)
                        .height(ComponentSizeRules.Button.bottomActionButtonHeight),
                    onClick = {
                        if (titleState.isNotEmpty() && descriptionState.isNotEmpty()) {
                            titleErrorState = false
                            descriptionErrorState = false
                            viewModel.sendIntent(
                                HomeIntent.SaveTask(
                                    title = titleState,
                                    description = descriptionState
                                )
                            )
                            coroutineScope.launch { state.hide() }
                        } else {
                            titleErrorState = titleState.isEmpty()
                            descriptionErrorState = descriptionState.isEmpty()
                        }
                    }
                ) {
                    Text(
                        text = stringResource(id = R.string.create),
                        fontSize = TextSizeRules.Button.bottomActionButton
                    )
                }
            }
        }
    }
}