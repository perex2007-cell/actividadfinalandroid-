package com.example.actividadfinalandroid.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.example.actividadfinalandroid.domain.repository.AuthRepository
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun register(email: String, pass: String): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = task.result?.user?.uid ?: ""
                        continuation.resume(Result.success(uid))
                    } else {
                        val exception = task.exception ?: Exception("Registration failed")
                        continuation.resume(Result.failure(exception))
                    }
                }
        }
    }

    override suspend fun login(email: String, pass: String): Result<String> {
        return suspendCancellableCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = task.result?.user?.uid ?: ""
                        continuation.resume(Result.success(uid))
                    } else {
                        val exception = task.exception ?: Exception("Login failed")
                        continuation.resume(Result.failure(exception))
                    }
                }
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUserUid(): String? {
        return firebaseAuth.currentUser?.uid
    }

    override fun isUserLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
