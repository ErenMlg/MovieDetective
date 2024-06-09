package com.softcross.moviedetective.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.extensions.bouncingClickable


@Composable
fun PopularPeoples(peopleList: List<String>) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
    ) {
        items(peopleList) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .shadow(8.dp, MaterialTheme.shapes.small)
                    .clip(MaterialTheme.shapes.small)
                    .width(160.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .bouncingClickable(onClick = {})
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movieimage),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = it,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
                )
            }
        }
    }
}