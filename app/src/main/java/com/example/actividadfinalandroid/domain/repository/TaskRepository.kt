package com.example.actividadfinalandroid.domain.repository

import com.example.actividadfinalandroid.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    suspend fun createTask(task: Task): Result<Unit>
    fun getTasks(userId: String): Flow<List<Task>>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun deleteTask(taskId: String): Result<Unit>
}
