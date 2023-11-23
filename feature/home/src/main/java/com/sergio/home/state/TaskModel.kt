package com.sergio.home.state

import com.challenge.model.Task
import com.challenge.model.TaskType

data class TaskModel(
    val taskList: List<Task>,
    val pendingList: List<Task>,
    val chartData: Map<String, Int>,
    val pendingCount: Int,
    val completedCount: Int,
    val personalTaskCount: Int,
    val teamTaskCount: Int,
)

internal fun List<Task>.toModel(): TaskModel {
    val descendedList = sortedBy { it.dueDate }
    val pendingList = descendedList.filter { it.complete.not() }
    return TaskModel(
        taskList = descendedList,
        pendingList = pendingList,
        chartData = pendingList
            .sortedBy { it.type }
            .groupBy { it.type }
            .map { it.key.value to it.value.size }
            .toMap(),
        pendingCount = count { it.complete.not() },
        completedCount = count { it.complete },
        personalTaskCount = count { it.type == TaskType.Personal },
        teamTaskCount = count { it.type == TaskType.Team },
    )
}