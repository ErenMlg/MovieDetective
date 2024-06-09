package com.softcross.moviedetective.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.domain.model.Actor
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.core.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {
    //Top Movies State
    private val _topMovieState = mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val topMovieState: State<ScreenState<List<Movie>>> get() = _topMovieState

    //Trend Movies State
    private val _trendMovieState = mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val trendMovieState: State<ScreenState<List<Movie>>> get() = _trendMovieState

    //Upcoming Movies State
    private val _upcomingMovieState =
        mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val upcomingMovieState: State<ScreenState<List<Movie>>> get() = _upcomingMovieState

    //Popular Peoples State
    private val _popularPeopleState =
        mutableStateOf<ScreenState<List<Actor>>>(ScreenState.Loading)
    val popularPeopleState: State<ScreenState<List<Actor>>> get() = _popularPeopleState

    //Discover Movies State
    private val _discoverMovieState =
        mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val discoverMovieState: State<ScreenState<List<Movie>>> get() = _discoverMovieState

    init {

        getTrendMovies()
        getTopMovies()
        discoverMovie(listOf(28, 12))

    }

    private fun getTopMovies() {
        viewModelScope.launch {
            contentRepository.getTop20Movie().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _topMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _topMovieState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _topMovieState.value = ScreenState.Loading
                    }
                }
            }
        }
    }

    private fun getTrendMovies() {
        viewModelScope.launch {
            contentRepository.getTrendMovies().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _trendMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _trendMovieState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _trendMovieState.value = ScreenState.Loading
                    }
                }
            }
        }
    }

    private fun discoverMovie(genreIDs: List<Int>) {
        val convertedList = genreIDs.toString().replace("[", "").replace("]", "")
        viewModelScope.launch {
            contentRepository.getMovieByGenre(convertedList).collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _discoverMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _discoverMovieState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _discoverMovieState.value = ScreenState.Loading
                    }
                }
            }
        }
    }

    private fun getComingMovies() {
        viewModelScope.launch {
            contentRepository.getComingSoonMovies().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _upcomingMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _upcomingMovieState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _upcomingMovieState.value = ScreenState.Loading
                    }
                }
            }
        }
    }

    private fun getPopularPeoples() {
    }
}
