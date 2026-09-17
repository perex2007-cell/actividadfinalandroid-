package com.example.actividadfinalandroid.domain.use_case.auth

import com.example.actividadfinalandroid.domain.repository.AuthRepository
import javax.inject.Inject

class GetCurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(): String? {
        return repository.getCurrentUserUid()
    }
}
