package com.example.actividadfinalandroid.data.repository

import com.example.actividadfinalandroid.data.local.dao.TaskDraftDao
import com.example.actividadfinalandroid.data.mapper.TaskMapper.toDomain
import com.example.actividadfinalandroid.data.mapper.TaskMapper.toEntity
import com.example.actividadfinalandroid.domain.model.TaskDraft
import com.example.actividadfinalandroid.domain.repository.DraftRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DraftRepositoryImpl @Inject constructor(
    private val dao: TaskDraftDao
) : DraftRepository {

    override suspend fun saveDraft(draft: TaskDraft) {
        dao.insertDraft(draft.toEntity())
    }

    override fun getDrafts(userId: String): Flow<List<TaskDraft>> {
        return dao.getDraftsForUser(userId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun updateDraft(draft: TaskDraft) {
        dao.updateDraft(draft.toEntity())
    }

    override suspend fun deleteDraft(draft: TaskDraft) {
        dao.deleteDraft(draft.toEntity())
    }
}
