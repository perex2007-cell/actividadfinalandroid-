package com.example.actividadfinalandroid.domain.use_case.auth

import com.example.actividadfinalandroid.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): Result<String> {
        return repository.login(email, pass)
    }
}
