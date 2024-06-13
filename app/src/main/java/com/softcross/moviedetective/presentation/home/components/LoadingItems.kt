package com.softcross.moviedetective.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.extensions.shimmerBackground

@Composable
fun LoadingHeaderContentItem(modifier: Modifier = Modifier) {
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
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                        .shimmerBackground()
                )
                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 8.dp, top = 8.dp)
                        .shimmerBackground()
                )
                Text(
                    text = "",
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(end = 8.dp, top = 8.dp)
                        .shimmerBackground()
                )
            }
            Box(
                Modifier
                    .width(125.dp)
                    .padding(start = 16.dp)
                    .shimmerBackground()
            ) {
                Text(
                    text = "",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
            Text(
                text = "",
                modifier = Modifier
                    .width(75.dp)
                    .padding(end = 8.dp, start = 16.dp)
                    .shimmerBackground()
            )
            Text(
                text = "",
                modifier = Modifier
                    .width(75.dp)
                    .padding(start = 16.dp, bottom = 8.dp)
                    .shimmerBackground()
            )
        }
        Box(
            modifier = Modifier
                .height(256.dp)
                .width(145.dp)
                .padding(16.dp)
                .clip(MaterialTheme.shapes.small)
                .shimmerBackground()
        )
    }
}

@Composable
fun LoadingContentItems() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(8.dp)
            .shadow(8.dp, MaterialTheme.shapes.small)
            .clip(MaterialTheme.shapes.small)
            .width(160.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .clip(MaterialTheme.shapes.small)
                .fillMaxWidth()
                .height(212.dp)
                .shimmerBackground()
        )
        CustomText(
            text = "",
            fontFamilyID = R.font.poppins_medium,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp).fillMaxWidth()
                .shimmerBackground()
        )
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp, start = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .align(alignment = Alignment.CenterVertically)
                    .padding(end = 8.dp, top = 8.dp)
                    .shimmerBackground()
            )
            CustomText(
                text = "",
                fontFamilyID = R.font.poppins_semi_bold,
                modifier = Modifier
                    .weight(0.3f)
                    .padding(start = 4.dp, end = 4.dp)
                    .shimmerBackground()
            )
            CustomText(
                text = "",
                textAlign = TextAlign.End,
                color = Color.Gray,
                modifier = Modifier
                    .weight(0.4f)
                    .padding(end = 16.dp)
                    .shimmerBackground()
            )
        }
    }
}

