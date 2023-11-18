package com.sergio.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.Surface
import androidx.compose.material3.Card
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.challenge.model.Task
import com.sergio.common.component.DefaultError
import com.sergio.common.component.Loading
import com.sergio.common.component.NoData
import com.sergio.common.theme.ElevationRules
import com.sergio.common.theme.PaddingRules
import com.sergio.common.theme.ShapeRules
import com.sergio.common.theme.SimplePlannerTheme
import com.sergio.home.R
import com.sergio.home.state.HomeState
import com.sergio.home.state.HomeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val taskState by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheetLayout(
        modifier = Modifier.padding(PaddingRules.Layout.all),
        sheetContent = {

        }
    ) {
        when(val state = taskState) {
            is HomeState.Loading -> Loading()
            is HomeState.Success -> {
                Column {
                    TaskInformation()
                    if (state.data.isNotEmpty()) {
                        TaskList(state.data)
                    } else {
                        NoData()
                    }
                }
            }
            is HomeState.Failure -> DefaultError()
        }
    }
}

@Composable
fun TaskInformation() {
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = ShapeRules.roundedCornerShape.small,
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(modifier = Modifier.padding(PaddingRules.Box.all)) {
                Text(
                    text = stringResource(id = R.string.pending_tasks),
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.background(Color.LightGray),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    )
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .background(Color.Green),
                        text = stringResource(id = R.string.pending_tasks),
                        color = MaterialTheme.colorScheme.onPrimary,
                        style = LocalTextStyle.current.merge(
                            TextStyle(
                                lineHeight = 1.em,
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = false
                                ),
                                lineHeightStyle = LineHeightStyle(
                                    alignment = LineHeightStyle.Alignment.Center,
                                    trim = LineHeightStyle.Trim.None
                                )
                            )
                        )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = ShapeRules.roundedCornerShape.small,
                elevation = ElevationRules.cardElevation()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "11")
                    Text(text = stringResource(id = R.string.completed_tasks))
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = ShapeRules.roundedCornerShape.small,
                elevation = ElevationRules.cardElevation()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "11")
                    Text(text = stringResource(id = R.string.pending_tasks))
                }
            }
        }
    }
}

@Composable
fun TaskList(taskList: List<Task>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = stringResource(id = R.string.task_list_title))
        LazyColumn {
            items(
                items = taskList,
                key = { task -> task.id }
            ) { task ->
                TaskItem(task = task)
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = ShapeRules.roundedCornerShape.small
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .fillMaxHeight()
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = task.title)
                Text(text = task.description)
                Text(text = task.dueDate)
            }
        }
    }
}



@Preview
@Composable
fun HomeScreenPreview() {
    SimplePlannerTheme {
        HomeScreen()
    }
}