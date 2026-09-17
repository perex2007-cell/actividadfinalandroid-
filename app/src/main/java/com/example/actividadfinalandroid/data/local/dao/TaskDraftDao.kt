package com.example.actividadfinalandroid.data.local.dao

import androidx.room.*
import com.example.actividadfinalandroid.data.local.entity.TaskDraftEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDraftDao {
    @Query("SELECT * FROM task_drafts WHERE ownerId = :userId ORDER BY savedAt DESC")
    fun getDraftsForUser(userId: String): Flow<List<TaskDraftEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDraft(draft: TaskDraftEntity)

    @Update
    suspend fun updateDraft(draft: TaskDraftEntity)

    @Delete
    suspend fun deleteDraft(draft: TaskDraftEntity)
}
