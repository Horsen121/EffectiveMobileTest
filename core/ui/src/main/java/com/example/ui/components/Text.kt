package com.example.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.theme.EffectiveMobileTestTheme

@Composable
fun AppTextField(
    text: String,
    onValueChange: (String) -> Unit,
    @StringRes placeholder: Int,
    modifier: Modifier = Modifier,
    height: Dp = 40.dp,
    @StringRes title: Int? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Done
    ),
    isError: Boolean = false,
    @StringRes supportingText: Int? = null
) {
    Column() {
        if(title != null) {
            LabelText(title, isHeadline = true)
            Spacer(Modifier.height(8.dp))
        }
        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = {
                if (text.isEmpty())
                    Text(
                        text = stringResource(placeholder),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
            },
            shape = MaterialTheme.shapes.extraLarge,
            leadingIcon = leadingIcon,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = keyboardOptions,
            isError = isError,
            supportingText = {
                if(isError && supportingText != null) BodyText(supportingText)
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(height)
        )
    }
}

@Composable
fun TitleText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = false
) {
    Text(
        text = stringResource(text),
        style = if(isHeadline) MaterialTheme.typography.titleLarge else MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier
    )
}

@Composable
fun LabelText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = stringResource(text),
        style = if(isHeadline) MaterialTheme.typography.titleSmall else MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
    )
}
@Composable
fun LabelText(
    text: String,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = if(isHeadline) MaterialTheme.typography.titleSmall else MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
    )
}

@Composable
fun BodyText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        text = stringResource(text),
        style = if(isHeadline) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodySmall,
        color = color,
        modifier = modifier
    )
}
@Composable
fun BodyText(
    text: String,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    maxLines: Int = 1,
    color: Color = MaterialTheme.colorScheme.onPrimary
) {
    Text(
        text = text,
        style = if(isHeadline) MaterialTheme.typography.bodyMedium else MaterialTheme.typography.bodySmall,
        color = color,
        maxLines = maxLines,
        modifier = modifier
    )
}

@Composable
fun ButtonText(
    @StringRes text: Int,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = stringResource(text),
        style = if(isHeadline) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
    )
}
@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
    isHeadline: Boolean = true,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        style = if(isHeadline) MaterialTheme.typography.labelLarge else MaterialTheme.typography.labelSmall,
        color = color,
        modifier = modifier
    )
}

@Preview
@Composable
private fun ButtonsPreview() {
    EffectiveMobileTestTheme(darkTheme = true, dynamicColor = false) {
        Scaffold(
            contentWindowInsets = WindowInsets(left = 16.dp, right = 16.dp, top = 32.dp),
            modifier = Modifier.fillMaxSize()
        ) { padding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                TitleText(R.string.ui_, isHeadline = true)
                TitleText(R.string.ui_)
                AppTextField("", {}, R.string.ui_)
                AppTextField("Text", {}, R.string.ui_)
                AppTextField("Text", {}, R.string.ui_, title = R.string.ui_)
                LabelText(R.string.ui_)
                LabelText(R.string.ui_, isHeadline = false)
                BodyText(R.string.ui_)
                BodyText(R.string.ui_, isHeadline = false)
            }
        }
    }
}