package com.softcross.moviedetective.presentation.home.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomAsyncImage
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.core.common.components.GenreItem
import com.softcross.moviedetective.core.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.domain.model.Movie

@Composable
fun HeaderContentItem(movie: Movie, modifier: Modifier) {
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
                CustomText(
                    text = movie.movieName,
                    fontFamilyID = R.font.poppins_semi_bold,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(end = 4.dp, start = 16.dp, top = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_star),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(top = 8.dp)
                )
                CustomText(
                    text = "%.2f".format(movie.imdb),
                    fontFamilyID = R.font.poppins_semi_bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(movie.genres) {
                    GenreItem(it)
                }
            }
            CustomText(
                text = movie.description,
                fontFamilyID = R.font.poppins_medium,
                fontSize = 10.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(end = 8.dp, start = 16.dp)
            )
            CustomText(
                text = movie.releaseDate.convertToFormattedDate(),
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
            )
        }
        CustomAsyncImage(
            model = movie.movieImage,
            contentDescription = "",
            alignment = Alignment.CenterStart,
            contentScale = ContentScale.Inside,
            modifier = Modifier
                .height(256.dp)
                .width(145.dp)
                .padding(16.dp)
                .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                .clip(MaterialTheme.shapes.small)
        )
    }
}

@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Composable
private fun HeaderContentPreview() {
    MaterialTheme {
        HeaderContentItem(
            Movie(
                movieID = 8778,
                movieName = "Adrian ParkerAdrian ParkerAdrian Parker",
                description = "urna",
                genres = listOf(),
                imdb = 8.4f,
                releaseDate = "2024-12-12",
                movieImage = "utroque"
            ),
            Modifier
        )
    }
}