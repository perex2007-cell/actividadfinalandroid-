package com.example.actividadfinalandroid.domain.use_case.draft

import com.example.actividadfinalandroid.domain.model.TaskDraft
import com.example.actividadfinalandroid.domain.repository.DraftRepository
import javax.inject.Inject

class SaveDraftUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    suspend operator fun invoke(draft: TaskDraft) {
        repository.saveDraft(draft)
    }
}
