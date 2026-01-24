package com.example.ui.components

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui.R
import com.example.ui.theme.EffectiveMobileTestTheme
import com.example.ui.theme.OKDarkColor
import com.example.ui.theme.OKLightColor
import com.example.ui.theme.VKColor

@Composable
fun AppButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.tertiary
) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.extraLarge,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = color,
            disabledContentColor = color
        ),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        ButtonText(text)
    }
}

@Composable
fun AppTextButton(
    @StringRes colorizedText: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes primaryText: Int? = null,
    isHeadline: Boolean = false
) {
    Row(modifier = modifier) {
        if(primaryText != null) {
            ButtonText(
                primaryText,
                isHeadline = isHeadline
            )
            Spacer(Modifier.width(8.dp))
        }
        ButtonText(
            colorizedText,
            isHeadline = isHeadline,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = modifier
                .clickable(onClick = onClick)
        )
    }
}

@Composable
fun VkOkButton(
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {
        Button(
            onClick = {
                // open https://vk.com/
            },
            shape = MaterialTheme.shapes.extraLarge,
            colors = ButtonDefaults.buttonColors().copy(
                containerColor = VKColor,
                disabledContentColor = VKColor
            ),
            modifier = Modifier
                .weight(1f)
//                .fillMaxWidth(1f)
        ) {
            Image(
                painterResource(R.drawable.ui_vk),
                null,
                Modifier.fillMaxHeight()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(
                    Brush.verticalGradient(listOf(OKLightColor, OKDarkColor))
                )
                .clickable {
                    // open https://ok.ru/
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painterResource(R.drawable.ui_ok_head),
                    null,
//                    Modifier.fillMaxHeight()
                )
                Image(
                    painterResource(R.drawable.ui_ok_body),
                    null,
//                    Modifier.fillMaxHeight()
                )
            }
        }
    }
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
                AppButton(text = R.string.ui_, onClick = {})
                AppTextButton(
                    R.string.ui_, {},
                    primaryText = R.string.ui_
                )
                VkOkButton()
            }
        }
    }
}