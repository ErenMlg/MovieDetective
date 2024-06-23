package com.softcross.moviedetective.presentation.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Actor
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    //Top Movies State
    private val _popularMovieState = mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val popularMovieState: State<ScreenState<List<Movie>>> get() = _popularMovieState

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
        getPopularMovies()
        discoverMovie(listOf(12, 28))
        getComingMovies()
        getPopularPeoples()
    }

    private fun getPopularMovies() {
        viewModelScope.launch {
            contentRepository.getPopularMovies().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _popularMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _popularMovieState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _popularMovieState.value = ScreenState.Loading
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

    fun discoverMovie(genreIDs: List<Int>) {
        viewModelScope.launch {
            contentRepository.getMovieByGenre(genreIDs.listToString()).collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _discoverMovieState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        if (result.result.isEmpty()) {
                            _discoverMovieState.value = ScreenState.Error("No Movie Found")
                        } else {
                            _discoverMovieState.value = ScreenState.Success(result.result)
                        }
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
        viewModelScope.launch {
            contentRepository.getPopularActors().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _popularPeopleState.value =
                            ScreenState.Error(result.exception.message.toString())
                    }

                    is NetworkResponseState.Success -> {
                        _popularPeopleState.value = ScreenState.Success(result.result)
                    }

                    is NetworkResponseState.Loading -> {
                        _popularPeopleState.value = ScreenState.Loading
                    }
                }
            }
        }
    }
}
