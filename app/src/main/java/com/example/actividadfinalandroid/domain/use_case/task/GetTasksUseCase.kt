package com.example.actividadfinalandroid.domain.use_case.task

import com.example.actividadfinalandroid.domain.model.Task
import com.example.actividadfinalandroid.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(userId: String): Flow<List<Task>> {
        return repository.getTasks(userId)
    }
}
