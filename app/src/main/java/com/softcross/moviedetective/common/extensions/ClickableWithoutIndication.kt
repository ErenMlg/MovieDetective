package com.softcross.moviedetective.common.extensions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.clickableWithoutIndicator(onClick: () -> Unit): Modifier = this then clickable(
    indication = null,
    interactionSource = remember { MutableInteractionSource() },
) {
    onClick()
}
