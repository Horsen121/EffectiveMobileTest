package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    private val _effects = Channel<LoginUiEffect>(Channel.BUFFERED)
    val effects = _effects.receiveAsFlow()

    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun onEmailChanged(input: String) {
        val sanitized = input.filter { ch -> ch.code !in 0x0400..0x04FF }
        _state.update { currentState ->
            currentState.copy(
                email = sanitized,
                emailErrorResId = null,
                isLoginEnabled = sanitized.isNotBlank() && currentState.password.isNotBlank()
            )
        }
    }

    fun onPasswordChanged(password: String) {
        _state.update { currentState ->
            currentState.copy(
                password = password,
                isLoginEnabled = currentState.email.isNotBlank() && password.isNotBlank()
            )
        }
    }

    fun onLoginClicked() {
        val email = _state.value.email.trim()
        if (!emailRegex.matches(email)) {
            _state.update { it.copy(emailErrorResId = R.string.login_email_error) }
            return
        }

        viewModelScope.launch {
            _effects.send(LoginUiEffect.NavigateToHome)
        }
    }
}