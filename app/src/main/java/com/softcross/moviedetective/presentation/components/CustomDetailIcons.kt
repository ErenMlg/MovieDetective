package com.softcross.moviedetective.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun CustomDetailIcons(
    drawableID: Int,
    onClick: () -> Unit,
    cardModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier,
    color: Color = LocalContentColor.current
) {
    Card(
        shape = CircleShape,
        modifier = cardModifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Icon(
            painter = painterResource(id = drawableID),
            contentDescription = "",
            modifier = iconModifier,
            tint = color
        )
    }
}