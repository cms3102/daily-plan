package com.challenge.model

data class Task(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: String,
    val complete: Boolean
)