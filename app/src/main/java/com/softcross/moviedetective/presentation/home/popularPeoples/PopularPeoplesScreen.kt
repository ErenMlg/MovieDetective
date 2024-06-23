package com.softcross.moviedetective.presentation.home.popularPeoples

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
import androidx.paging.compose.collectAsLazyPagingItems
import com.softcross.moviedetective.R
import com.softcross.moviedetective.presentation.components.CustomText
import com.softcross.moviedetective.presentation.components.LoadingContentItems
import com.softcross.moviedetective.presentation.components.PopularPeopleItem


@Composable
fun PopularPeoplesScreen(
    viewModel: PopularPeoplesViewModel = hiltViewModel()
) {
    val actor = viewModel.popularPeoplesPagingFlow.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        item(span = { GridItemSpan(2) }) {
            CustomText(
                text = "Popular Peoples",
                fontFamilyID = R.font.poppins_semi_bold,
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, bottom = 8.dp)
            )
        }
        if (actor.loadState.refresh is LoadState.Loading) {
            items(2) {
                LoadingContentItems()
            }
        } else {
            items(actor.itemCount) {
                actor[it]?.let { actor ->
                    PopularPeopleItem(
                        people = actor
                    )
                }
            }
        }
    }
}