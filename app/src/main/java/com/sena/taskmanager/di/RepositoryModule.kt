package com.sena.taskmanager.di

import com.sena.taskmanager.data.repository.AuthRepositoryImpl
import com.sena.taskmanager.data.repository.DraftRepositoryImpl
import com.sena.taskmanager.data.repository.TaskRepositoryImpl
import com.sena.taskmanager.domain.repository.AuthRepository
import com.sena.taskmanager.domain.repository.DraftRepository
import com.sena.taskmanager.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTaskRepository(
        taskRepositoryImpl: TaskRepositoryImpl
    ): TaskRepository

    @Binds
    @Singleton
    abstract fun bindDraftRepository(
        draftRepositoryImpl: DraftRepositoryImpl
    ): DraftRepository
}
