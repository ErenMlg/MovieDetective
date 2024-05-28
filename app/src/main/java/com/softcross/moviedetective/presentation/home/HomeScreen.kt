package com.softcross.moviedetective.presentation.home

import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import com.airbnb.lottie.model.content.GradientColor
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.extensions.startOffsetForPage
import com.softcross.moviedetective.core.domain.model.Genre
import com.softcross.moviedetective.core.domain.model.Movie
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen() {
    val fakeMovies = listOf(
        Movie(
            movieID = "pretium",
            movieName = "Randall Peck",
            description = "ignota",
            genres = listOf(
                Genre("1", "Action"),
                Genre("1", "Horror")
            ),
            imdb = 2.3f,
            releaseDate = "2019-03-27",
            movieImage = "fastidii"
        ),
        Movie(
            movieID = "proin",
            movieName = "Stephan Hill",
            description = "risus",
            genres = listOf(
                Genre("1", "Action")
            ),
            imdb = 6.7f,
            releaseDate = "2020-03-27",
            movieImage = "fermentum"
        ),
        Movie(
            movieID = "has",
            movieName = "Abby Holman",
            description = "quaestio",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("1", "Mystery")
            ),
            imdb = 10.11f,
            releaseDate = "2021-03-27",
            movieImage = "gravida"
        ),
        Movie(
            movieID = "ponderum",
            movieName = "Wilma Arnold",
            description = "feugait",
            genres = listOf(
                Genre("1", "Drama"),
                Genre("1", "Science Fiction")
            ),
            imdb = 14.15f,
            releaseDate = "2024-03-27",
            movieImage = "tempus"
        )
    )
    val pagerState = rememberPagerState { fakeMovies.size }
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            HeaderContentItem(
                movie = fakeMovies[page],
                Modifier.graphicsLayer {
                    val startOffset = pagerState.startOffsetForPage(page)
                    translationX = size.width * (startOffset * 1)
                    alpha = (2f - startOffset) / 2f
                    val blur = (startOffset * 20f).coerceAtLeast(0.1f)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        renderEffect = RenderEffect
                            .createBlurEffect(
                                blur, blur, Shader.TileMode.DECAL
                            ).asComposeRenderEffect()
                    }
                    val scale = 1f - (startOffset * .1f)
                    scaleX = scale
                    scaleY = scale
                }
            )
        }
    }
}

@Composable
fun HeaderContentItem(movie: Movie, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(250.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Box(
            modifier = Modifier
                .width(400.dp)
                .height(200.dp)
        ) {
            Card(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .height(125.dp)
                    .width(300.dp)
                    .padding(end = 16.dp),
                elevation = CardDefaults.cardElevation(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 50.dp, top = 8.dp, bottom = 8.dp, end = 8.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = movie.movieName,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = Color.Black,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 16.dp)
                        )
                        Image(
                            Icons.Filled.Star,
                            contentDescription = "",
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = movie.imdb.toString(),
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            color = Color.Black,
                            modifier = Modifier.weight(0.15f)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        LazyRow {
                            itemsIndexed(movie.genres) { index, item ->
                                Card(
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    ),
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Box(
                                        Modifier.background(
                                            Brush.linearGradient(
                                                listOf(
                                                    if (index == 0) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.inverseSurface,
                                                    Color.White
                                                )
                                            )
                                        )
                                    ) {
                                        Text(
                                            text = item.genreName,
                                            fontSize = 8.sp,
                                            fontFamily = FontFamily(Font(R.font.poppins_semi_bold)),
                                            color = Color.White,
                                            modifier = Modifier.padding(horizontal = 8.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Text(
                        text = movie.description,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 10.sp,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = movie.releaseDate,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        fontFamily = FontFamily(Font(R.font.poppins_semi_bold))
                    )
                }
            }
            Card(
                modifier = Modifier
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.movieimage),
                    contentDescription = "",
                    alignment = Alignment.CenterStart,
                    contentScale = ContentScale.FillHeight,
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.S)
@Preview(name = "Light", uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true)
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}