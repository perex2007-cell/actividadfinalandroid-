package com.sena.taskmanager.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.sena.taskmanager.data.mapper.TaskMapper.toDocument
import com.sena.taskmanager.data.mapper.TaskMapper.toDomain
import com.sena.taskmanager.data.remote.model.TaskDocument
import com.sena.taskmanager.domain.model.Task
import com.sena.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : TaskRepository {

    override suspend fun createTask(task: Task): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            val collection = firestore.collection("tasks")
            val docRef = if (task.id.isEmpty()) collection.document() else collection.document(task.id)
            val taskWithId = task.copy(id = docRef.id)
            docRef.set(taskWithId.toDocument())
                .addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(Result.failure(t.exception ?: Exception("Failed to create task")))
                    }
                }
        }
    }

    override fun getTasks(userId: String): Flow<List<Task>> {
        return callbackFlow {
            val listener = firestore.collection("tasks")
                .whereEqualTo("ownerId", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        close(error)
                        return@addSnapshotListener
                    }
                    if (snapshot != null) {
                        val list = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(TaskDocument::class.java)?.copy(id = doc.id)?.toDomain()
                        }
                        trySend(list)
                    }
                }
            awaitClose { listener.remove() }
        }
    }

    override suspend fun updateTask(task: Task): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection("tasks").document(task.id)
                .set(task.toDocument())
                .addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(Result.failure(t.exception ?: Exception("Failed to update task")))
                    }
                }
        }
    }

    override suspend fun deleteTask(taskId: String): Result<Unit> {
        return suspendCancellableCoroutine { continuation ->
            firestore.collection("tasks").document(taskId)
                .delete()
                .addOnCompleteListener { t ->
                    if (t.isSuccessful) {
                        continuation.resume(Result.success(Unit))
                    } else {
                        continuation.resume(Result.failure(t.exception ?: Exception("Failed to delete task")))
                    }
                }
        }
    }
}
