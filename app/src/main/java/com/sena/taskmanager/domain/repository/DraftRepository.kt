package com.sena.taskmanager.domain.repository

import com.sena.taskmanager.domain.model.TaskDraft
import kotlinx.coroutines.flow.Flow

interface DraftRepository {
    suspend fun saveDraft(draft: TaskDraft)
    fun getDrafts(userId: String): Flow<List<TaskDraft>>
    suspend fun updateDraft(draft: TaskDraft)
    suspend fun deleteDraft(draft: TaskDraft)
}
