package com.softcross.moviedetective.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.extensions.shimmerBackground

@Composable
fun LoadingLazyRow(){
    LazyRow(Modifier.padding(8.dp)) {
        items(3) {
            LoadingContentItem()
        }
    }
}

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
            Text(
                text = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                    .shimmerBackground()
            )
            Text(
                text = "",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(125.dp)
                    .shimmerBackground()
            )
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
fun LoadingContentItem() {
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
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
                .height(212.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .shimmerBackground()
        )
        CustomText(
            text = "",
            fontFamilyID = R.font.poppins_medium,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp)
                .fillMaxWidth()
                .shimmerBackground()
        )
        Spacer(modifier = Modifier.size(16.dp))
        CustomText(
            text = "",
            fontFamilyID = R.font.poppins_medium,
            textAlign = TextAlign.Start,
            fontSize = 14.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .shimmerBackground()
        )
        Spacer(modifier = Modifier.size(8.dp))
    }
}

@Preview
@Composable
fun LoadingHeaderContentItemPreview() {
    MaterialTheme {
        LoadingHeaderContentItem()
    }
}

