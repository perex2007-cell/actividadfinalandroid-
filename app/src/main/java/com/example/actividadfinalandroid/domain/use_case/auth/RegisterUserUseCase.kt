package com.example.actividadfinalandroid.domain.use_case.auth

import com.example.actividadfinalandroid.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): Result<String> {
        return repository.register(email, pass)
    }
}
