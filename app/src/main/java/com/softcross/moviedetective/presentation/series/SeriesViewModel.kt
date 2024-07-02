package com.softcross.moviedetective.presentation.series

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.common.extensions.listToString
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    //Popular Series State
    private val _popularSeriesState =
        mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val popularSeriesState: State<ScreenState<List<Content>>> get() = _popularSeriesState

    //Top Series State
    private val _topSeriesState = mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val topSeriesState: State<ScreenState<List<Content>>> get() = _topSeriesState

    //Upcoming Series State
    private val _upcomingSeriesState =
        mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val upcomingSeriesState: State<ScreenState<List<Content>>> get() = _upcomingSeriesState

    //Airing Series State
    private val _airingSeriesState =
        mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val airingSeriesState: State<ScreenState<List<Content>>> get() = _airingSeriesState

    //Discover Series State
    private val _discoverSeriesState =
        mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val discoverSeriesState: State<ScreenState<List<Content>>> get() = _discoverSeriesState

    init {
        getPopularSeries()
        getTopSeries()
        getUpcomingSeries()
        getAiringSeries()
        getDiscoverSeries(listOf(10759, 16))
    }

    private fun getPopularSeries() = viewModelScope.launch {
        contentRepository.getPopularSeries().collect { result ->
            when (result) {
                is NetworkResponseState.Error -> _popularSeriesState.value =
                    ScreenState.Error(result.exception.message.toString())

                is NetworkResponseState.Success -> _popularSeriesState.value =
                    ScreenState.Success(result.result)

                is NetworkResponseState.Loading -> _popularSeriesState.value = ScreenState.Loading
            }
        }
    }

    private fun getTopSeries() = viewModelScope.launch {
        contentRepository.getTopSeries().collect { result ->
            when (result) {
                is NetworkResponseState.Error -> _topSeriesState.value =
                    ScreenState.Error(result.exception.message.toString())

                is NetworkResponseState.Loading -> _topSeriesState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _topSeriesState.value =
                    ScreenState.Success(result.result)
            }
        }
    }

    private fun getUpcomingSeries() = viewModelScope.launch {
        contentRepository.getComingSeries().collect { result ->
            when (result) {
                is NetworkResponseState.Error -> _upcomingSeriesState.value =
                    ScreenState.Error(result.exception.message.toString())

                is NetworkResponseState.Loading -> _upcomingSeriesState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _upcomingSeriesState.value =
                    ScreenState.Success(result.result)
            }
        }
    }

    private fun getAiringSeries() = viewModelScope.launch {
        contentRepository.getAiringSeries().collect { result ->
            when (result) {
                is NetworkResponseState.Error -> _airingSeriesState.value =
                    ScreenState.Error(result.exception.message.toString())

                is NetworkResponseState.Loading -> _airingSeriesState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _airingSeriesState.value =
                    ScreenState.Success(result.result)
            }
        }
    }

    private fun getDiscoverSeries(genreIDs: List<Int>) = viewModelScope.launch {
        contentRepository.getSeriesByGenre(genreIDs.listToString()).collect { result ->
            when (result) {
                is NetworkResponseState.Error -> _discoverSeriesState.value =
                    ScreenState.Error(result.exception.message.toString())

                is NetworkResponseState.Loading -> _discoverSeriesState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _discoverSeriesState.value =
                    ScreenState.Success(result.result)
            }
        }
    }
}