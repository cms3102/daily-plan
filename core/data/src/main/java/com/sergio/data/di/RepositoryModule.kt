package com.sergio.data.di

import com.sergio.data.repository.TaskRepository
import com.sergio.data.repository.TaskRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    @Singleton
    internal abstract fun bindTaskRepository(taskRepositoryImpl: TaskRepositoryImpl): TaskRepository

}