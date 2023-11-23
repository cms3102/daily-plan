package com.challenge.detail.ui

import android.view.WindowInsets.Side
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.atLeast
import androidx.constraintlayout.compose.atLeastWrapContent
import androidx.constraintlayout.compose.atMost
import androidx.constraintlayout.compose.atMostWrapContent
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.challenge.detail.R
import com.challenge.detail.navigation.Detail
import com.challenge.detail.state.DetailIntent
import com.challenge.detail.state.DetailState
import com.challenge.detail.state.DetailViewModel
import com.challenge.model.Task
import com.sergio.common.component.BottomActionButton
import com.sergio.common.component.DefaultError
import com.sergio.common.component.DefaultScaffold
import com.sergio.common.component.Loading
import com.sergio.common.theme.Black10
import com.sergio.common.theme.PaddingRules
import com.sergio.common.theme.PastelPurple
import com.sergio.common.theme.ShapeRules
import com.sergio.common.theme.TextSizeRules

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: DetailViewModel = hiltViewModel()
) {
    val taskState by viewModel.state.collectAsStateWithLifecycle()

    DefaultScaffold(
        navController = navController,
        showBackButton = true,
        destination = Detail,
        topAppBarColors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        ConstraintLayout(modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        )
        {
            val (
                topBackground,
                bottomBackground,
                titleBox,
                descriptionBox,
                completionButton
            ) = createRefs()

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .constrainAs(topBackground) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.value(230.dp)
                    }
            )

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .constrainAs(bottomBackground) {
                        top.linkTo(topBackground.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
            )

            when(val state = taskState) {
                is DetailState.Loading -> Loading()
                is DetailState.Success -> {
                    val task = state.data
                    TitleBox(
                        task = task,
                        modifier = Modifier
                            .constrainAs(titleBox) {
                                top.linkTo(topBackground.bottom)
                                bottom.linkTo(topBackground.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                width = Dimension.fillToConstraints
                                height = Dimension.value(120.dp)
                            }
                            .padding(horizontal = 28.dp)
                            .padding(bottom = 14.dp),
                    )
                    DescriptionBox(
                        description = task.description,
                        modifier = Modifier
                            .constrainAs(descriptionBox) {
                                linkTo(
                                    top = titleBox.bottom,
                                    bottom = parent.bottom,
                                    start = parent.start,
                                    end = parent.end,
                                    verticalBias = 0f
                                )
                                width = Dimension.fillToConstraints
                                height = Dimension.preferredWrapContent.atLeast(400.dp)
                            }
                            .padding(horizontal = 28.dp, vertical = 16.dp)
                            .clip(ShapeRules.roundedCornerShape.medium)
                    )
                    CompletionButton(
                        modifier = Modifier
                            .constrainAs(completionButton) {
                                linkTo(
                                    top = descriptionBox.bottom,
                                    bottom = parent.bottom,
                                    start = parent.start,
                                    end = parent.end,
                                    verticalBias = 0f
                                )
                                width = Dimension.fillToConstraints
                            }
                            .padding(horizontal = 28.dp, vertical = 20.dp),
                        buttonText = stringResource(
                            id = if (task.complete) {
                                R.string.completed
                            } else {
                                R.string.change_to_completion
                            }
                        ),
                        enabled = !task.complete,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (task.complete) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.primary,
                            contentColor = if (task.complete) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        viewModel.sendIntent(
                            DetailIntent.CompleteTask(task.id)
                        )
                    }
                }
                is DetailState.Failure -> DefaultError()
            }

        }
    }
}

@Composable
fun TitleBox(task: Task, modifier: Modifier) {
    Card(
        modifier = modifier,
        shape = ShapeRules.roundedCornerShape.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .background(Color(task.typeColor))
                    .fillMaxHeight()
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(14.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    text = task.title,
                    color = MaterialTheme.colorScheme.onSecondary,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = stringResource(id = R.string.task_type_format).format(task.type.value),
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 10.sp,
                        letterSpacing = 0.sp,
                    )
                    Text(
                        modifier = Modifier.padding(start = 8.dp),
                        text = stringResource(id = R.string.due_date_format).format(task.dueDate),
                        color = MaterialTheme.colorScheme.onTertiary,
                        fontSize = 10.sp,
                        letterSpacing = 0.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun DescriptionBox(description: String, modifier: Modifier) {
    Box(modifier = modifier
        .background(Color.White)
        .padding(16.dp)
    ) {
        // 여기서 자식 컴포저블이 fillMaxSize, fillMaxHeight를 하게 되면 constraint height 옵션이 깨짐.
        // height가 wrap content가 되게 해줘야 자동 사이즈 조절이 됨.
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = description,
            color = Color.Gray,
        )
    }
}

@Composable
fun CompletionButton(
    modifier: Modifier,
    buttonText: String,
    enabled: Boolean = true,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit
) {

    BottomActionButton(
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        onClick = { onClick.invoke() }
    ) {
        Text(
            text = buttonText,
            fontSize = TextSizeRules.Button.bottomActionButton
        )
    }
}