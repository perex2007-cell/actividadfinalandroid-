package com.example.actividadfinalandroid.domain.use_case.task

import com.example.actividadfinalandroid.domain.model.Task
import com.example.actividadfinalandroid.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(task: Task): Result<Unit> {
        return repository.updateTask(task)
    }
}
