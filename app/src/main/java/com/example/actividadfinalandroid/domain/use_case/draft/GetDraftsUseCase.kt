package com.example.actividadfinalandroid.domain.use_case.draft

import com.example.actividadfinalandroid.domain.model.TaskDraft
import com.example.actividadfinalandroid.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDraftsUseCase @Inject constructor(
    private val repository: DraftRepository
) {
    operator fun invoke(userId: String): Flow<List<TaskDraft>> {
        return repository.getDrafts(userId)
    }
}
