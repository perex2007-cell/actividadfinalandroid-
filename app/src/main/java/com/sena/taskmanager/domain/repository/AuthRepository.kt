package com.sena.taskmanager.domain.repository

interface AuthRepository {
    suspend fun register(email: String, pass: String): Result<String>
    suspend fun login(email: String, pass: String): Result<String>
    suspend fun logout()
    fun getCurrentUserUid(): String?
    fun isUserLoggedIn(): Boolean
}
