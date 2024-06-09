package com.softcross.moviedetective.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.softcross.moviedetective.core.common.extensions.bouncingClickable
import com.softcross.moviedetective.core.domain.model.Movie


@Composable
fun TrendMovies(movieList: List<Movie>) {
    LazyRow(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(movieList) {
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
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.Inside,
                    modifier = Modifier
                        .padding(8.dp)
                        .shadow(elevation = 8.dp, MaterialTheme.shapes.small)
                        .clip(MaterialTheme.shapes.small)
                )
                Text(
                    text = it.movieName,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = Color.Black,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = "",
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(24.dp)
                            .weight(0.2f)
                            .padding(start = 16.dp)
                    )
                    Text(
                        text = it.imdb.toString(),
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        color = Color.Black,
                        modifier = Modifier
                            .weight(0.3f)
                            .padding(start = 4.dp)
                    )
                    Text(
                        text = "2024",
                        fontSize = 12.sp,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        color = Color.Gray,
                        modifier = Modifier
                            .weight(0.6f)
                            .padding(end = 16.dp)
                    )
                }

            }
        }

    }
}