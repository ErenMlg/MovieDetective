package com.softcross.moviedetective.presentation.home.popularMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.cachedIn
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularMoviesViewModel @Inject constructor(
    contentRepository: ContentRepository
) : ViewModel() {

    val moviePagingFlow = contentRepository.getPopularMoviesByPage()
        .flow
        .cachedIn(viewModelScope)


}