package com.sergio.home.state

import com.challenge.model.Task
import com.challenge.model.TaskType

data class TaskModel(
    val taskList: List<Task>,
    val chartData: Map<String, Int>,
    val pendingCount: Int,
    val completedCount: Int,
    val personalTaskCount: Int,
    val teamTaskCount: Int,
)

internal fun List<Task>.toModel(): TaskModel {
    return TaskModel(
        taskList = this,
        chartData = groupBy { it.type }
            .map { it.key.value to it.value.size }
            .toMap(),
        pendingCount = count { it.complete.not() },
        completedCount = count { it.complete },
        personalTaskCount = count { it.type == TaskType.Personal },
        teamTaskCount = count { it.type == TaskType.Team },
    )
}