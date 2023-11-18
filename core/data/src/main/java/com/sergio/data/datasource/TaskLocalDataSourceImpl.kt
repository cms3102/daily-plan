package com.sergio.data.datasource

import com.sergio.data.database.dao.TaskDao
import com.sergio.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class TaskLocalDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao
) : TaskLocalDataSource {

    override suspend fun loadAllTasks(): List<TaskEntity> {
        return taskDao.loadAllTasks()
    }

    override suspend fun saveTask(task: TaskEntity) {
        taskDao.saveTask(task)
    }

}