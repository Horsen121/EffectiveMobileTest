package com.example.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ui.utils.conditional

@Composable
fun BackgroundRow(
    modifier: Modifier = Modifier,
    isRound: Boolean = false,
    isCard: Boolean = true,
    height: Dp = 22.dp,
    content: (@Composable () -> Unit)
) {
    Card(
        colors = CardDefaults.cardColors().copy(
            containerColor = if(isCard) MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondary,
            disabledContentColor = if(isCard) MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.5f) else MaterialTheme.colorScheme.secondary
        ),
        shape = if (isRound) CircleShape else CardDefaults.shape,
        modifier = Modifier
            .conditional(isCard && height != 22.dp) {
                height(22.dp)
            }
            .conditional(!isCard && height != 22.dp) {
                height(height)
            }
            .conditional(!isCard && height == 22.dp) {
                fillMaxSize()
            }
//            .height(if(isCard && height != 22.dp) 22.dp else height)
            .conditional(isRound) {
                widthIn(min = height, max = height)
                aspectRatio(1f, true)
            }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .conditional(!isRound) {
                    wrapContentSize()
                }
                .conditional(isRound) {
                    fillMaxSize()
                }
        ) {
            content()
        }
    }
}