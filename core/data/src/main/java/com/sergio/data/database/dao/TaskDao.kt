package com.sergio.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sergio.data.database.entity.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM ${TaskEntity.NAME}")
    fun loadAllTasksFlow(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM ${TaskEntity.NAME}")
    suspend fun loadAllTasks(): List<TaskEntity>

    @Insert
    suspend fun saveTask(task: TaskEntity)

}