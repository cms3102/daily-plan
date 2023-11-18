package com.sergio.data.di

import com.sergio.data.datasource.TaskLocalDataSource
import com.sergio.data.datasource.TaskLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {

    @Binds
    @Singleton
    internal abstract fun bindTaskLocalDataSource(taskLocalDataSourceImpl: TaskLocalDataSourceImpl): TaskLocalDataSource

}