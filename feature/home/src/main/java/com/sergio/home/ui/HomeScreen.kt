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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.challenge.detail.navigation.navigateToDetail
import com.challenge.model.Task
import com.sergio.common.component.DefaultError
import com.sergio.common.component.Loading
import com.sergio.common.component.NoData
import com.sergio.common.theme.BottomSheetShape
import com.sergio.common.theme.DeepYellow
import com.sergio.common.theme.ElevationRules
import com.sergio.common.theme.LightPurple
import com.sergio.common.theme.PaddingRules
import com.sergio.common.theme.PastelGreen
import com.sergio.common.theme.PastelPurple
import com.sergio.common.theme.PastelYellow
import com.sergio.common.theme.ShapeRules
import com.sergio.common.theme.SimplePlannerTheme
import com.sergio.home.R
import com.sergio.home.state.HomeState
import com.sergio.home.state.HomeViewModel
import com.sergio.home.state.TaskModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )
    val coroutineScope = rememberCoroutineScope()
    val taskState by viewModel.state.collectAsStateWithLifecycle()

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetShape = BottomSheetShape,
        sheetContent = {
            RegistrationScreen(
                state = bottomSheetState,
                viewModel = viewModel
            )
        },
    ) {
        when(val state = taskState) {
            is HomeState.Loading -> Loading()
            is HomeState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            horizontal = PaddingRules.horizontal,
                            vertical = PaddingRules.vertical
                        )
                ) {
                    TitleBar()
                    val taskData = state.data
                    TaskInformation(taskData)
                    if (taskData.taskList.isNotEmpty()) {
                        TaskList(
                            taskList = taskData.taskList,
                            onClickItem = { task ->
                                navController.navigateToDetail(task)
                            }
                        )
                    } else {
                        NoData()
                    }
                }
                RegistrationButton {
                    coroutineScope.launch {
                        bottomSheetState.show()
                    }
                }
            }
            is HomeState.Failure -> DefaultError()
        }
    }
}

@Composable
fun TitleBar() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .height(60.dp)
            .wrapContentHeight(),
        text = stringResource(id = R.string.home_title),
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp,
        fontFamily = FontFamily.SansSerif
    )
}

@Composable
fun TaskInformation(data: TaskModel) {
    val chartColors = listOf(
        DeepYellow,
        PastelPurple,
    )
    Column(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shape = ShapeRules.roundedCornerShape.medium,
            color = MaterialTheme.colorScheme.primary
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(PaddingRules.Box.all)
                ) {
                    Text(
                        text = stringResource(id = R.string.pending_tasks),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(chartColors.first())
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp),
                            text = stringResource(id = R.string.pending_tasks),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = LocalTextStyle.current.merge(
                                TextStyle(
                                    platformStyle = PlatformTextStyle(
                                        includeFontPadding = false
                                    )
                                )
                            )
                        )
                    }
                }

                PieChart(
                    data = mapOf(
                        "미완료" to 40,
                        "완료" to 60
                    ),
                    colors = chartColors,
                    modifier = Modifier.weight(1f)
                )

            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Card(
                modifier = Modifier.weight(1f),
                shape = ShapeRules.roundedCornerShape.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = data.completedCount.toString())
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(id = R.string.completed_tasks),
                        color = Color.Gray
                    )
                }
            }
            Card(
                modifier = Modifier.weight(1f),
                shape = ShapeRules.roundedCornerShape.medium,
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = data.pendingCount.toString())
                    Text(
                        modifier = Modifier.padding(top = 8.dp),
                        text = stringResource(id = R.string.pending_tasks),
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun TaskList(
    taskList: List<Task>,
    onClickItem: (task: Task) -> Unit
) {
    Text(
        modifier = Modifier.padding(top = 20.dp, bottom = 8.dp),
        text = stringResource(id = R.string.task_list_title),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(
            items = taskList,
            key = { task -> task.id }
        ) { task ->
            TaskItem(
                task = task,
                onClick = onClickItem
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    task: Task,
    onClick: (task: Task) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .height(120.dp),
        shape = ShapeRules.roundedCornerShape.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary),
        onClick = {
            onClick.invoke(task)
        }
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .background(PastelPurple)
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = task.title,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp)
                        .weight(1f)
                    ,
                    text = task.description,
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = task.dueDate,
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
fun RegistrationButton(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        FloatingActionButton(
            modifier = Modifier.padding(end = 20.dp, bottom = 30.dp),
            shape = RoundedCornerShape(12.dp),
            backgroundColor = MaterialTheme.colorScheme.primary,
            onClick = { onClick.invoke() }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "Task Registration Button"
            )
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    SimplePlannerTheme {
        HomeScreen(rememberNavController())
    }
}