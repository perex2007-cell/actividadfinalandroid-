package com.sena.taskmanager.data.remote.model

import com.google.firebase.Timestamp

data class TaskDocument(
    val id: String = "",
    val ownerId: String = "",
    val title: String = "",
    val description: String = "",
    val completed: Boolean = false,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)
