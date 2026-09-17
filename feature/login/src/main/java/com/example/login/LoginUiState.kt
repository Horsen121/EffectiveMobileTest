package com.example.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailErrorResId: Int? = null,
    val isLoginEnabled: Boolean = false,
    val isLoading: Boolean = false
)

sealed interface LoginUiEffect {
    data object NavigateToHome : LoginUiEffect
}