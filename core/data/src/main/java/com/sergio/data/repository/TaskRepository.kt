package com.sergio.data.repository

import com.challenge.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {

    val tasks: Flow<List<Task>>

    suspend fun loadAllTasksFlow(): Flow<List<Task>>

    suspend fun loadAllTasks(): List<Task>

    suspend fun saveTask(task: Task)

}