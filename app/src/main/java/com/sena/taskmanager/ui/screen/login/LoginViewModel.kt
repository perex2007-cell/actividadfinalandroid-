package com.sena.taskmanager.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sena.taskmanager.domain.usecase.auth.LoginUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    fun login(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = LoginState(error = "Fields cannot be empty")
            return
        }
        viewModelScope.launch {
            _state.value = LoginState(isLoading = true)
            val result = loginUserUseCase(email, pass)
            if (result.isSuccess) {
                _state.value = LoginState(isSuccess = true)
            } else {
                _state.value = LoginState(error = result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }

    fun resetState() {
        _state.value = LoginState()
    }
}
