package com.softcross.moviedetective.presentation.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun CustomDetailIcons(
    drawableID: Int,
    onClick: () -> Unit,
    cardModifier: Modifier = Modifier,
    iconModifier: Modifier = Modifier
) {
    Card(
        shape = CircleShape,
        modifier = cardModifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary
        )
    ) {
        Icon(
            painterResource(id = drawableID),
            contentDescription = "",
            iconModifier
        )
    }
}