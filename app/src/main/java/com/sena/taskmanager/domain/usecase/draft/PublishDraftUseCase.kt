package com.sena.taskmanager.domain.usecase.draft

import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.model.TaskDraft
import com.sena.taskmanager.domain.repository.DraftRepository
import com.sena.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class PublishDraftUseCase @Inject constructor(
    private val taskRepository: TaskRepository,
    private val draftRepository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft): Result<Unit> {
        val now = System.currentTimeMillis()
        val task = Task(
            id = "", 
            ownerId = draft.ownerId,
            title = draft.title,
            description = draft.description,
            completed = false,
            createdAt = now,
            updatedAt = now
        )
        val result = taskRepository.createTask(task)
        if (result.isSuccess) {
            draftRepository.deleteDraft(draft)
        }
        return result
    }
}
