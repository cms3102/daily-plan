package com.sergio.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.model.TaskType
import com.sergio.common.component.BottomActionButton
import com.sergio.common.theme.ComponentSizeRules
import com.sergio.common.theme.LightGray
import com.sergio.common.theme.SimplePlannerTheme
import com.sergio.common.theme.TextSizeRules
import com.sergio.home.R
import com.sergio.home.state.HomeIntent
import com.sergio.home.state.HomeViewModel
import kotlinx.coroutines.launch

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
    val typeList = TaskType.values().toList()
    var selectedType by remember { mutableStateOf(typeList.first()) }
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    if (state.currentValue == ModalBottomSheetValue.Hidden) {
        titleState = ""
        descriptionState = ""
        focusManager.clearFocus()
        selectedType = TaskType.Personal
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(top = 16.dp, bottom = 45.dp),
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.tertiary)

            )
            Divider(modifier = Modifier.padding(top = 30.dp, bottom = 22.dp))
            Column(modifier = Modifier.fillMaxWidth()) {
                InputFields(
                    titleState = titleState,
                    titleErrorState = titleErrorState,
                    onTitleChange = { input -> titleState = input },
                    descriptionState = descriptionState,
                    descriptionErrorState = descriptionErrorState,
                    onDescriptionChange = { input -> descriptionState = input }
                )
                TypeSelector(
                    menus = typeList,
                    currentSelection = selectedType
                ) { menu ->
                    selectedType = menu
                }
                BottomActionButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 60.dp, bottom = 10.dp),
                    onClick = {
                        if (titleState.isNotEmpty() && descriptionState.isNotEmpty()) {
                            titleErrorState = false
                            descriptionErrorState = false
                            viewModel.sendIntent(
                                HomeIntent.SaveTask(
                                    title = titleState,
                                    description = descriptionState,
                                    type = selectedType
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

@Composable
fun InputFields(
    titleState: String,
    titleErrorState: Boolean,
    onTitleChange: (value: String) -> Unit,
    descriptionState: String,
    descriptionErrorState: Boolean,
    onDescriptionChange: (value: String) -> Unit,
) {
    Text(text = stringResource(id = R.string.request_task_title))
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        value = titleState,
        onValueChange = onTitleChange,
        label = { Text(text = stringResource(id = R.string.task_title)) },
        singleLine = true,
        isError = titleErrorState,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
    )
    Text(
        modifier = Modifier.padding(top = 20.dp),
        text = stringResource(id = R.string.request_task_description)
    )
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp),
        value = descriptionState,
        onValueChange = onDescriptionChange,
        label = { Text(text = stringResource(id = R.string.task_description)) },
        singleLine = true,
        isError = descriptionErrorState,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
    )
}

@Composable
private fun TypeSelector(
    menus: List<TaskType>,
    currentSelection: TaskType,
    onClick: (menu: TaskType) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        Text(text = stringResource(id = R.string.request_select_type))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            menus.forEach { menu ->
                val isSelected = currentSelection == menu
                val colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    contentColor = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .weight(1f),
                    colors = colors,
                    onClick = { onClick.invoke(menu) }
                ) {
                    Text(text = menu.value)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Preview
@Composable
fun RegistrationScreenPreview() {
    SimplePlannerTheme {
        RegistrationScreen(
            rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
            hiltViewModel()
        )
    }
}