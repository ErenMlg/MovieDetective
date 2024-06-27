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
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = mutableStateOf<ScreenState<MovieDetail>>(ScreenState.Loading)
    val detailState: State<ScreenState<MovieDetail>> get() = _detailState

    private val _actorMovieState = mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val actorMovieState: State<ScreenState<List<Movie>>> get() = _actorMovieState

    init {
        savedStateHandle.get<Int>("id")?.let { movieId ->
            getMovieDetail(movieId)
        }
    }

    private fun getMovieDetail(movieId: Int) = viewModelScope.launch {
        contentRepository.getMovieDetails(movieId).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _detailState.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _detailState.value = ScreenState.Loading

                is NetworkResponseState.Success -> {
                    onMovieClicked(response.result.cast.first().id)
                    _detailState.value =
                        ScreenState.Success(response.result)
                }
            }
        }
    }

    fun onMovieClicked(actorID: Int) = viewModelScope.launch {
        contentRepository.getActorMovies(actorID).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _actorMovieState.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _actorMovieState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _actorMovieState.value =
                    ScreenState.Success(response.result)
            }
        }
    }
}