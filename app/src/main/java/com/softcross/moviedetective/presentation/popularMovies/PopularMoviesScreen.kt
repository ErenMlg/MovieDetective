package com.softcross.moviedetective.presentation.popularMovies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.softcross.moviedetective.R
import com.softcross.moviedetective.core.common.components.CustomText
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.presentation.components.LoadingContentItems
import com.softcross.moviedetective.presentation.components.TrendContentItem

@Composable
fun PopularMoviesScreen(
    viewModel: PopularMoviesViewModel = hiltViewModel(),
    onBack: () -> Unit
) {
    val movies = viewModel.moviePagingFlow.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxWidth()) {
        when (movies.loadState.refresh) {
            is LoadState.Loading -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                ) {
                    item(span = { GridItemSpan(2) }) {
                        CustomText(
                            text = "Popular Movies",
                            fontFamilyID = R.font.poppins_semi_bold,
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
                        )
                    }
                    items(6) {
                        LoadingContentItems()
                    }

                }
            }

            else -> {
                PopularMoviesSuccessContent(
                    movies = movies,
                    onBack = onBack,
                )
            }
        }
    }
}

@Composable
fun PopularMoviesSuccessContent(
    movies: LazyPagingItems<Movie>,
    onBack: () -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        content = {
            item(span = { GridItemSpan(2) }) {
                CustomText(
                    text = "Popular Movies",
                    fontFamilyID = R.font.poppins_semi_bold,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
                )
            }
            items(movies.itemCount) {
                movies[it]?.let { movie ->
                    TrendContentItem(
                        movie = movie
                    )
                }
            }
        })
}