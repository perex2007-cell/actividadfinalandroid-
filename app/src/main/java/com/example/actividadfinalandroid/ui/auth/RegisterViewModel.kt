package com.example.actividadfinalandroid.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadfinalandroid.domain.use_case.auth.RegisterUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isSuccess: Boolean = false
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(RegisterState())
    val state: StateFlow<RegisterState> = _state.asStateFlow()

    fun register(email: String, pass: String) {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = RegisterState(error = "Fields cannot be empty")
            return
        }
        viewModelScope.launch {
            _state.value = RegisterState(isLoading = true)
            val result = registerUserUseCase(email, pass)
            if (result.isSuccess) {
                _state.value = RegisterState(isSuccess = true)
            } else {
                _state.value = RegisterState(error = result.exceptionOrNull()?.message ?: "Registration failed")
            }
        }
    }

    fun resetState() {
        _state.value = RegisterState()
    }
}
