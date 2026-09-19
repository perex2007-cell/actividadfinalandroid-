package com.sena.taskmanager.data.mapper

import com.google.firebase.Timestamp
import com.sena.taskmanager.data.local.entity.TaskDraftEntity
import com.sena.taskmanager.data.remote.model.TaskDocument
import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.model.TaskDraft
import java.util.Date

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

    fun Task.toDocument(): TaskDocument {
        return TaskDocument(
            id = id,
            ownerId = ownerId,
            title = title,
            description = description,
            completed = completed,
            createdAt = if (createdAt > 0) Timestamp(Date(createdAt)) else Timestamp.now(),
            updatedAt = Timestamp.now()
        )
    }

    fun TaskDocument.toDomain(): Task {
        return Task(
            id = id,
            ownerId = ownerId,
            title = title,
            description = description,
            completed = completed,
            createdAt = createdAt?.toDate()?.time ?: 0L,
            updatedAt = updatedAt?.toDate()?.time ?: 0L
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
