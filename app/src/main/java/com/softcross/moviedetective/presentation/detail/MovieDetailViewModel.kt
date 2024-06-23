package com.softcross.moviedetective.presentation.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state = mutableStateOf<ScreenState<MovieDetail>>(ScreenState.Loading)
    val state: State<ScreenState<MovieDetail>> get() = _state

    init {
        savedStateHandle.get<String>("id")?.let { movieId ->
            getMovieDetail(movieId.toInt())
        }
    }

    private fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        contentRepository.getMovieDetails(movieId).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _state.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _state.value = ScreenState.Loading
                is NetworkResponseState.Success -> _state.value =
                    ScreenState.Success(response.result)
            }
        }
    }
}