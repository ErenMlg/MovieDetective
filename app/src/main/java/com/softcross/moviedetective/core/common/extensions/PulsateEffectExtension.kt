package com.softcross.moviedetective.core.common.extensions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ViewState { Pressed, Idle }

fun Modifier.bouncingClickable(
    onClick: () -> Unit
) = composed {
    var viewState by remember { mutableStateOf(ViewState.Idle) }
    val scale by animateFloatAsState(if (viewState == ViewState.Pressed) 0.90f else 1f, label = "")

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = onClick
        )
        .pointerInput(viewState) {
            awaitPointerEventScope {
                viewState = if (viewState == ViewState.Pressed) {
                    waitForUpOrCancellation()
                    ViewState.Idle
                } else {
                    awaitFirstDown(false)
                    ViewState.Pressed
                }
            }
        }
}