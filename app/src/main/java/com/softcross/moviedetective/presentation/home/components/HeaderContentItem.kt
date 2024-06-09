package com.softcross.moviedetective.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.softcross.moviedetective.core.common.components.CustomAsyncImage
import com.softcross.moviedetective.core.common.extensions.convertToFormattedDate
import com.softcross.moviedetective.core.domain.model.Movie

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
                Text(
                    text = movie.movieName,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Start,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp, start = 16.dp, top = 8.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_star),
                    contentDescription = "",
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .size(24.dp)
                        .align(alignment = Alignment.CenterVertically)
                        .padding(end = 8.dp, top = 8.dp)
                )
                Text(
                    text = movie.imdb.toString(),
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    modifier = Modifier
                        .weight(0.15f)
                        .padding(end = 8.dp, top = 8.dp)
                )
            }
            LazyRow(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                items(movie.genres) {
                    GenreItem(it)
                }
            }
            Text(
                text = movie.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp,
                color = Color.Black,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                modifier = Modifier.padding(end = 8.dp, start = 16.dp)
            )
            Text(
                text = movie.releaseDate.convertToFormattedDate(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.poppins_regular)),
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