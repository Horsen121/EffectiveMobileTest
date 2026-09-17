package com.example.login

import app.cash.turbine.test
import com.example.test.MainDispatcherRule
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `onEmailChanged filters cyrillic characters`() {
        // Given
        val input = "testПривет@gmail.com"

        // When
        viewModel.onEmailChanged(input)

        // Then
        assertEquals("test@gmail.com", viewModel.state.value.email)
    }

    @Test
    fun `isLoginEnabled is false when password or email is empty`() {
        viewModel.onEmailChanged("test@gmail.com")
        assertFalse(viewModel.state.value.isLoginEnabled)

        viewModel.onPasswordChanged("12345")
        assertTrue(viewModel.state.value.isLoginEnabled)
    }

    @Test
    fun `onLoginClicked with invalid email sets error`() {
        viewModel.onEmailChanged("invalid-email")
        viewModel.onPasswordChanged("password")

        viewModel.onLoginClicked()

        assertEquals(R.string.login_email_error, viewModel.state.value.emailErrorResId)
    }

    @Test
    fun `onLoginClicked with valid credentials emits NavigateToHome effect`() = runTest {
        viewModel.onEmailChanged("dev@effective.com")
        viewModel.onPasswordChanged("secret123")

        viewModel.effects.test {
            viewModel.onLoginClicked()

            val effect = awaitItem()
            assertTrue(effect is LoginUiEffect.NavigateToHome)
            assertNull(viewModel.state.value.emailErrorResId)
        }
    }
}