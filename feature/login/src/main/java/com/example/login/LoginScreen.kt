package com.example.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AppButton
import com.example.ui.components.AppTextButton
import com.example.ui.components.AppTextField
import com.example.ui.components.BodyText
import com.example.ui.components.TitleText
import com.example.ui.components.VkOkButton
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    paddingValues: PaddingValues,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effects) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is LoginUiEffect.NavigateToHome -> onLoginSuccess()
            }
        }
    }

    LoginScreenContent(
        state = state,
        onEmailChange = viewModel::onEmailChanged,
        onPasswordChange = viewModel::onPasswordChanged,
        onLoginClick = viewModel::onLoginClicked,
        modifier = Modifier.padding(paddingValues)
    )
}

@Composable
fun LoginScreenContent(
    state: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        TitleText(
            text = R.string.login_title,
            isHeadline = true,
            modifier = Modifier.padding(top = 100.dp, bottom = 16.dp)
        )

        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            AppTextField(
                text = state.email,
                onValueChange = onEmailChange,
                placeholder = R.string.login_email_placeholder,
                title = R.string.login_email_title,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                isError = state.emailErrorResId != null,
                supportingText = {
                    state.emailErrorResId?.let { errorRes ->
                        BodyText(
                            text = errorRes,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )

            AppTextField(
                text = state.password,
                onValueChange = onPasswordChange,
                placeholder = R.string.login_password_placeholder,
                title = R.string.login_password_title,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
        }

        AppButton(
            text = R.string.login_button,
            enabled = state.isLoginEnabled,
            onClick = onLoginClick
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppTextButton(
                colorizedText = R.string.login_reg,
                onClick = {},
                primaryText = R.string.login_hasnot_account,
                enabled = false
            )
            AppTextButton(
                colorizedText = R.string.login_forgot_password,
                onClick = {},
                enabled = false
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.surfaceVariant
        )

        VkOkButton(modifier = Modifier.padding(top = 16.dp))
    }
}


@Preview
@Composable
private fun LoginScreenPreview() {
    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            LoginScreen({}, padding)
        }
    }
}