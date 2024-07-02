package com.softcross.moviedetective.presentation.home.moreMovies

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

@HiltViewModel
class MoreMoviesViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<Flow<PagingData<Content>>>(emptyFlow())
    val state = _state

    init {
        getMovies()
    }

    private fun getMovies() {
        _state.value = when (savedStateHandle.get<Int>("id")) {
            0 -> contentRepository.getComingMoviesByPage().flow.cachedIn(viewModelScope)
            1 -> contentRepository.getMoviesByGenreByPage(
                savedStateHandle.get<String>("genres") ?: ""
            ).flow.cachedIn(viewModelScope)

            2 -> contentRepository.getPopularMoviesByPage().flow.cachedIn(viewModelScope)
            3 -> contentRepository.getTrendMoviesByPage().flow.cachedIn(viewModelScope)
            else -> emptyFlow()
        }
    }

    fun getMoviesByGenre(genre: String) {
        _state.value = contentRepository.getMoviesByGenreByPage(genre).flow.cachedIn(viewModelScope)
    }
}