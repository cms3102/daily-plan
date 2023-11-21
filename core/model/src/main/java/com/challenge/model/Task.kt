package com.challenge.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val dueDate: String,
    val complete: Boolean,
    val type: TaskType
)

enum class TaskType(val value: String) {
    Personal("Personal"), Team("Team")
}