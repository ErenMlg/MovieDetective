package com.softcross.moviedetective.presentation.home.comingMovies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComingMoviesViewModel @Inject constructor(
    contentRepository: ContentRepository
): ViewModel(){

    val comingMoviePagingFlow = contentRepository.getComingMoviesByPage()
        .flow
        .cachedIn(viewModelScope)


}