package com.sergio.data.datasource

import com.sergio.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    fun loadAllTasksFlow(): Flow<List<TaskEntity>>

    suspend fun loadAllTasks(): List<TaskEntity>

    suspend fun saveTask(task: TaskEntity)

    suspend fun loadTask(id: Long): TaskEntity

    suspend fun completeTask(id: Long)



}