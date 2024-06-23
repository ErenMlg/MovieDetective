package com.softcross.moviedetective.presentation.home.trendMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrendMoviesViewModel @Inject constructor(
    contentRepository: ContentRepository
) : ViewModel() {

    val trendMoviePagingFlow = contentRepository.getTrendMoviesByPage()
        .flow
        .cachedIn(viewModelScope)

}