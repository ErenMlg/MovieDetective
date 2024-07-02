package com.softcross.moviedetective.presentation.home.moreMovies

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
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.presentation.components.ActorItem
import com.softcross.moviedetective.presentation.components.ComingContentItem
import com.softcross.moviedetective.presentation.components.ContentItem
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.LoadingContentItem

enum class DataType(val typeID: Int, val title: String) {
    ComingMovie(0, "Coming Movies"), DiscoverMovie(1, "Discover Movies"),
    PopularMovie(2, "Popular Movies"), TrendMovie(3, "Trend Movies")
}

@Composable
fun MoreMovieScreen(
    dataTypeID: Int,
    genreList: String? = null,
    viewModel: MoreMoviesViewModel = hiltViewModel(),
    onClick: (Int) -> Unit
) {
    val result = viewModel.state.value.collectAsLazyPagingItems()
    val dataType = DataType.entries[dataTypeID]
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            CustomText(
                text = dataType.title,
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
            )
        }
        if (dataType.typeID == 1) {
            item(span = { GridItemSpan(2) }) {
                DiscoverContent(genreList, onSelect = remember {
                    { viewModel.getMoviesByGenre(it) }
                })
            }
        }
        if (result.loadState.refresh is LoadState.Loading) {
            items(6) {
                LoadingContentItem()
            }
        } else {
            items(result.itemCount) {
                result[it]?.let { res ->
                    when (dataType.typeID) {
                        0 -> {
                            ComingContentItem(
                                content = res,
                                onClick = remember {
                                    { onClick(res.id) }
                                }
                            )
                        }

                        else -> {
                            ContentItem(
                                content = res,
                                onClick = remember {
                                    { onClick(res.id) }
                                }
                            )
                        }
                    }

                }
            }

        }
    }
}

@Composable
fun DiscoverContent(
    genreList: String?,
    onSelect: (String) -> Unit
) {
    val selectedGenreList = remember { genreList?.toIntList() ?: mutableListOf<Int>() }
    val allGenresList = remember { GenreList.getMovieGenreList() }
    LazyRow(
        Modifier.padding(vertical = 8.dp)
    ) {
        items(items = allGenresList, key = { it.genreID }) { genre ->
            var isSelected by remember { mutableStateOf(selectedGenreList.contains(genre.genreID)) }
            val onClick: () -> Unit = remember {
                {
                    if (selectedGenreList.contains(genre.genreID)) {
                        selectedGenreList.remove(genre.genreID)
                    } else {
                        selectedGenreList.add(genre.genreID)
                    }
                    isSelected = selectedGenreList.contains(genre.genreID)
                    if (selectedGenreList.isEmpty()) {
                        onSelect(listOf(12, 28).listToString())
                    } else {
                        onSelect(selectedGenreList.listToString())
                    }
                }
            }
            Card(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clickableWithoutIndicator(onClick = onClick),
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

