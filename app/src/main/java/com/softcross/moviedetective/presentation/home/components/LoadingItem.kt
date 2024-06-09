package com.softcross.moviedetective.presentation.home.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R

@Composable
fun LoadingHeaderContentItem(modifier: Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 750
                0.7f at 400
            },
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = modifier
            .padding(bottom = 32.dp)
            .width(400.dp)
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .height(125.dp)
                .width(300.dp)
                .padding(end = 16.dp)
                .shadow(8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(start = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                        .background(Color.LightGray.copy(alpha = alpha))
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 8.dp, top = 8.dp)
                        .background(Color.LightGray.copy(alpha = alpha))
                )
                Text(
                    text = "",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(end = 8.dp, top = 8.dp)
                        .background(Color.LightGray.copy(alpha = alpha))
                )
            }
            Box(
                Modifier
                    .width(125.dp)
                    .padding(start = 16.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            ) {
                Text(
                    text = "",
                    fontSize = 8.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Text(
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier
                    .width(75.dp)
                    .padding(end = 8.dp, start = 16.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
            Text(
                text = "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
                modifier = Modifier
                    .width(75.dp)
                    .padding(start = 16.dp, bottom = 8.dp)
                    .background(Color.LightGray.copy(alpha = alpha))
            )
        }
        Box(
            modifier = Modifier
                .height(256.dp)
                .width(145.dp)
                .padding(16.dp)
                .clip(MaterialTheme.shapes.small)
                .background(Color.LightGray.copy(alpha = alpha))
        )
    }
}