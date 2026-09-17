package com.example.actividadfinalandroid.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(email: String, pass: String): Result<String>
    suspend fun login(email: String, pass: String): Result<String>
    suspend fun logout()
    fun getCurrentUserUid(): String?
    fun isUserLoggedIn(): Boolean
}
