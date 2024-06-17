package com.softcross.moviedetective.presentation.popularMovies

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Movie
import com.softcross.moviedetective.domain.repository.ContentRepository
import kotlinx.coroutines.launch
import java.util.Collections.addAll
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _state = mutableStateOf<ScreenState<List<Movie>>>(ScreenState.Loading)
    val state: State<ScreenState<List<Movie>>> get() = _state

    private val movieList: MutableList<Movie> = mutableListOf()

    fun getPopularMovies() = viewModelScope.launch {
        contentRepository.getTop20Movie().collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _state.value =
                    ScreenState.Error(response.exception.message.toString())
                is NetworkResponseState.Loading -> _state.value = ScreenState.Loading
                is NetworkResponseState.Success -> {
                    movieList.addAll(response.result)
                    _state.value = ScreenState.Success(movieList)
                }
            }
        }
    }

}