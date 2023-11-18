package com.sergio.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sergio.data.database.SimplePlannerDataBase
import com.sergio.data.database.dao.TaskDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Provides
    @Singleton
    internal fun provideSimplePlannerDatabase(
        @ApplicationContext context: Context,
    ): SimplePlannerDataBase {
        return Room.databaseBuilder(
            context = context,
            klass = SimplePlannerDataBase::class.java,
            name = SimplePlannerDataBase.NAME
        ).build()
    }

    @Provides
    @Singleton
    internal fun provideTaskDao(database: SimplePlannerDataBase): TaskDao {
        return database.taskDao()
    }

}