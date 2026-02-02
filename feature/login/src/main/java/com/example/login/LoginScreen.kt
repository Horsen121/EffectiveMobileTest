package com.example.login

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    paddingValues: PaddingValues
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        TitleText(
            text = R.string.login_,
            isHeadline = true,
            modifier = Modifier
                .padding(
                    top = 140.dp.minus(paddingValues.calculateTopPadding()),
                    bottom = 16.dp
                )
        )

        Column {
            AppTextField(
                text = email,
                onValueChange = {
                    val filtered = it.filter { ch ->
                        ch.code !in 0x0400..0x04FF
                    }
                    email = filtered
                },
                placeholder = R.string.login_email_placeholder,
                title = R.string.login_email_title,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                isError = isError,
                supportingText = {if(isError) BodyText(R.string.login_email_error)}
            )

            AppTextField(
                text = password,
                onValueChange = { password = it },
                placeholder = R.string.login_password_placeholder,
                title = R.string.login_password_title,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                )
            )
        }


        AppButton(
            text = R.string.login_,
            onClick = {
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    isError = true
                } else {
                    if(email.isNotEmpty() && password.isNotEmpty())
                        onLoginSuccess()
                }
            }
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
            Modifier
                .height(1.dp)
                .padding(vertical = 16.dp),
            color = MaterialTheme.colorScheme.surface
        )

        VkOkButton(Modifier.padding(top = 32.dp))
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