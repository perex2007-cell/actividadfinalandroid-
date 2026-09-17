package com.example.actividadfinalandroid.domain.use_case.auth

import com.example.actividadfinalandroid.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() {
        repository.logout()
    }
}
