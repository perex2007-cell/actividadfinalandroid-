package com.example.actividadfinalandroid.data.mapper

import com.example.actividadfinalandroid.data.local.entity.TaskDraftEntity
import com.example.actividadfinalandroid.domain.model.Task
import com.example.actividadfinalandroid.domain.model.TaskDraft

object TaskMapper {
    fun TaskDraftEntity.toDomain(): TaskDraft {
        return TaskDraft(
            id = this.id,
            ownerId = this.ownerId,
            title = this.title,
            description = this.description,
            savedAt = this.savedAt
        )
    }

    fun TaskDraft.toEntity(): TaskDraftEntity {
        return TaskDraftEntity(
            id = this.id,
            ownerId = this.ownerId,
            title = this.title,
            description = this.description,
            savedAt = this.savedAt
        )
    }

    fun taskToMap(task: Task): Map<String, Any> {
        return mapOf(
            "id" to task.id,
            "ownerId" to task.ownerId,
            "title" to task.title,
            "description" to task.description,
            "completed" to task.completed,
            "createdAt" to task.createdAt,
            "updatedAt" to task.updatedAt
        )
    }

    fun mapToTask(id: String, map: Map<String, Any>): Task {
        return Task(
            id = id,
            ownerId = map["ownerId"] as? String ?: "",
            title = map["title"] as? String ?: "",
            description = map["description"] as? String ?: "",
            completed = map["completed"] as? Boolean ?: false,
            createdAt = (map["createdAt"] as? Long) ?: ((map["createdAt"] as? Number)?.toLong() ?: 0L),
            updatedAt = (map["updatedAt"] as? Long) ?: ((map["updatedAt"] as? Number)?.toLong() ?: 0L)
        )
    }
}
