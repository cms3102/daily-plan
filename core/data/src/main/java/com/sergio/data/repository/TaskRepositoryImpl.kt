package com.sergio.data.repository

import com.challenge.model.Task
import com.sergio.data.database.entity.TaskEntity
import com.sergio.data.database.entity.toDomain
import com.sergio.data.database.entity.toEntity
import com.sergio.data.datasource.TaskLocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val taskLocalDataSource: TaskLocalDataSource
) : TaskRepository {

    override val tasks: Flow<List<Task>>
        get() = taskLocalDataSource.loadAllTasksFlow()
            .map { taskList ->
                taskList.map { task ->
                    task.toDomain()
                }
            }.flowOn(ioDispatcher)

    override suspend fun loadAllTasksFlow(): Flow<List<Task>> {
        return taskLocalDataSource.loadAllTasksFlow()
            .map { taskList ->
                taskList.map { task ->
                    task.toDomain()
                }
            }
    }

    override suspend fun loadAllTasks(): List<Task> {
        return taskLocalDataSource.loadAllTasks()
            .map { task ->
                task.toDomain()
            }
    }

    override suspend fun saveTask(task: Task) {
        taskLocalDataSource.saveTask(task.toEntity())
    }

    override suspend fun loadTask(id: Long): Task {
        return taskLocalDataSource
            .loadTask(id)
            .toDomain()
    }

    override suspend fun completeTask(id: Long) {
        taskLocalDataSource.completeTask(id)
    }

}