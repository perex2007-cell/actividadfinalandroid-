package com.example.actividadfinalandroid.di

import com.example.actividadfinalandroid.data.repository.AuthRepositoryImpl
import com.example.actividadfinalandroid.data.repository.DraftRepositoryImpl
import com.example.actividadfinalandroid.data.repository.TaskRepositoryImpl
import com.example.actividadfinalandroid.domain.repository.AuthRepository
import com.example.actividadfinalandroid.domain.repository.DraftRepository
import com.example.actividadfinalandroid.domain.repository.TaskRepository
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
