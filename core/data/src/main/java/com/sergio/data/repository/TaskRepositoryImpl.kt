package com.sergio.data.repository

import com.challenge.model.Task
import com.sergio.data.database.entity.TaskEntity
import com.sergio.data.database.entity.toDomain
import com.sergio.data.datasource.TaskLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val taskLocalDataSource: TaskLocalDataSource
) : TaskRepository {
    override val tasks: Flow<List<Task>>
        get() = flow { emit(loadAllTasks()) }

    override suspend fun loadAllTasks(): List<Task> {
        return taskLocalDataSource.loadAllTasks()
            .map { task ->
                task.toDomain()
            }
    }

    override suspend fun saveTask(title: String, description: String, dueDate: String) {
        taskLocalDataSource.saveTask(
            TaskEntity(
                title = title,
                description = description,
                dueDate = dueDate
            )
        )
    }

}