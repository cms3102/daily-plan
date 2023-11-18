package com.sergio.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sergio.data.database.dao.TaskDao
import com.sergio.data.database.entity.TaskEntity

@Database(
    entities = [TaskEntity::class],
    version = VERSION,
    exportSchema = true
)
internal abstract class SimplePlannerDataBase : RoomDatabase() {
    internal abstract fun taskDao(): TaskDao

    companion object {
        internal const val NAME = "simple-planner-database"
    }

}

private const val VERSION = 1