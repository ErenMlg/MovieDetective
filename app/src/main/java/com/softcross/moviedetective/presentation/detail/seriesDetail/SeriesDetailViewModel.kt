package com.softcross.moviedetective.presentation.detail.seriesDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.Content
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.model.SeriesDetail
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailState = mutableStateOf<ScreenState<SeriesDetail>>(ScreenState.Loading)
    val detailState: State<ScreenState<SeriesDetail>> get() = _detailState

    private val _actorSeriesState = mutableStateOf<ScreenState<List<Content>>>(ScreenState.Loading)
    val actorSeriesState: State<ScreenState<List<Content>>> get() = _actorSeriesState

    init {
        savedStateHandle.get<Int>("id")?.let { seriesID ->
            getSeriesDetail(seriesID)
        }
    }

    private fun getSeriesDetail(seriesID: Int) = viewModelScope.launch {
        contentRepository.getSeriesDetails(seriesID).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _detailState.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _detailState.value = ScreenState.Loading

                is NetworkResponseState.Success -> {
                    onActorClicked(response.result.cast.first().id)
                    _detailState.value =
                        ScreenState.Success(response.result)
                }
            }
        }
    }

    fun onActorClicked(actorID: Int) = viewModelScope.launch {
        contentRepository.getActorSeries(actorID).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _actorSeriesState.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _actorSeriesState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _actorSeriesState.value =
                    ScreenState.Success(response.result)
            }
        }
    }
}