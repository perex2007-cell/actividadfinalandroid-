package com.sena.taskmanager.domain.usecase.draft

import com.sena.taskmanager.domain.model.TaskDraft
import com.sena.taskmanager.domain.repository.DraftRepository
import javax.inject.Inject

class DeleteDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft) {
        repository.deleteDraft(draft)
    }
}
