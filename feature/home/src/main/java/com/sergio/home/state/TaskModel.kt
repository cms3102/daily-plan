package com.sergio.home.state

import com.challenge.model.Task

data class TaskModel(
    val taskList: List<Task>,
    val pendingCount: Int,
    val completedCount: Int
)

internal fun List<Task>.toModel(): TaskModel {
    return TaskModel(
        taskList = this,
        pendingCount = count { it.complete.not() },
        completedCount = count { it.complete }
    )
}