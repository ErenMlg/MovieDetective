package com.softcross.moviedetective.presentation.home.discoverMovies

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.softcross.moviedetective.R
import com.softcross.moviedetective.common.GenreList
import com.softcross.moviedetective.common.extensions.clickableWithoutIndicator
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.common.extensions.toIntList
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.ErrorScreen
import com.softcross.moviedetective.presentation.components.LoadingContentItems
import com.softcross.moviedetective.presentation.components.TrendContentItem


@Composable
fun DiscoverMoviesScreen(
    viewModel: DiscoverMoviesViewModel = hiltViewModel(),
    genreList: String?
) {
    val selectedGenreList = remember { genreList?.toIntList() ?: mutableListOf<Int>() }
    val movies = viewModel.state.value.collectAsLazyPagingItems()
    val allGenresList = remember { GenreList.getMovieGenreList() }
    var isSelected by remember {
        mutableStateOf(false)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            CustomText(
                text = "Discover Movies",
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
            )
        }
        item(span = { GridItemSpan(2) }) {
            LazyRow(
                Modifier.padding(vertical = 8.dp)
            ) {
                items(items = allGenresList, key = { it.genreID }) { genre ->
                    isSelected = selectedGenreList.contains(genre.genreID)
                    Card(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .clickableWithoutIndicator {
                                if (selectedGenreList.contains(genre.genreID)) {
                                    selectedGenreList.remove(genre.genreID)
                                } else {
                                    selectedGenreList.add(genre.genreID)
                                }
                                isSelected = selectedGenreList.contains(genre.genreID)
                                if (selectedGenreList.isEmpty()) {
                                    viewModel.getMoviesByGenre("12,28")
                                } else {
                                    viewModel.getMoviesByGenre(selectedGenreList.listToString())
                                }
                            },
                        elevation = CardDefaults.cardElevation(4.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.secondary else Color.White
                        )
                    ) {
                        CustomText(
                            text = genre.genreName,
                            color = if (isSelected) Color.White else Color.DarkGray,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }

        if (movies.loadState.refresh is LoadState.Loading) {
            items(6) {
                LoadingContentItems()
            }
        } else {
            items(movies.itemCount) {
                movies[it]?.let { movie ->
                    TrendContentItem(
                        movie = movie,
                        {}
                    )
                }
            }
        }

        if (movies.itemCount == 0) {
            item(span = { GridItemSpan(2) }) {
                ErrorScreen(
                    message = "No movies found"
                )
            }
        }
    }
}
