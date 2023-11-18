package com.sergio.data.datasource

import com.sergio.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

interface TaskLocalDataSource {

    suspend fun loadAllTasks(): List<TaskEntity>

    suspend fun saveTask(task: TaskEntity)



}