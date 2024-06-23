package com.softcross.moviedetective.presentation.home.discoverMovies

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class DiscoverMoviesViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<Flow<PagingData<Movie>>>(emptyFlow())
    val state = _state

    init {
        savedStateHandle.get<String>("genres")?.let { genres ->
            getMoviesByGenre(genres)
        }
    }

    fun getMoviesByGenre(genre: String) {

        _state.value = contentRepository.getMoviesByGenreByPage(genre).flow.cachedIn(viewModelScope)
    }
}