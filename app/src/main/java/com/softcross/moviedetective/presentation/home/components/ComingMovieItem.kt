package com.softcross.moviedetective.presentation.home.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
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
import com.softcross.moviedetective.core.common.extensions.calculateRemainingDays
import com.softcross.moviedetective.core.domain.model.Movie


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ComingSoon(movieList: List<Movie>) {
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
                Box {
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
                    Card(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "${calculateRemainingDays(it.releaseDate)} days",
                            fontSize = 10.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = Color.Black,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                    }
                }
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
                Text(
                    text = it.releaseDate,
                    fontSize = 12.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(bottom = 8.dp, start = 16.dp)
                )
            }
        }
    }
}