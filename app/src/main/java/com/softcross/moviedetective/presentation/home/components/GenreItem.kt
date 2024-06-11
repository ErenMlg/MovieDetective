package com.softcross.moviedetective.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomText

@Composable
fun GenreItem(genreID: Int, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.padding(end = 8.dp)
    ) {
        Box(
            Modifier.background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.secondary,
                        Color.White
                    ),
                    tileMode = TileMode.Repeated
                )
            )
        ) {
            CustomText(
                text = "Action",
                modifier = Modifier.padding(horizontal = 8.dp),
                color = Color.White,
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 8.sp
            )
        }
    }
}