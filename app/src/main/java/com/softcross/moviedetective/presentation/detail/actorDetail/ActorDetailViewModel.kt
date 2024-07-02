package com.softcross.moviedetective.presentation.detail.actorDetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.model.ActorDetail
import com.softcross.moviedetective.domain.model.MovieDetail
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActorDetailViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {

    private val _detailState = mutableStateOf<ScreenState<ActorDetail>>(ScreenState.Loading)
    val detailState: State<ScreenState<ActorDetail>> get() = _detailState

    init {
        savedStateHandle.get<Int>("id")?.let { seriesID ->
            getActorDetail(seriesID)
        }
    }

    private fun getActorDetail(actorID: Int) = viewModelScope.launch {
        contentRepository.getActorDetails(actorID).collect { response ->
            when (response) {
                is NetworkResponseState.Error -> _detailState.value =
                    ScreenState.Error(response.exception.message ?: "Error")

                is NetworkResponseState.Loading -> _detailState.value = ScreenState.Loading

                is NetworkResponseState.Success -> _detailState.value =
                    ScreenState.Success(response.result)
            }
        }
    }

}