package com.sena.taskmanager.domain.usecase.task

import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(
    private val repository: TaskRepository
) {
    operator fun invoke(userId: String): Flow<List<Task>> {
        return repository.getTasks(userId)
    }
}
