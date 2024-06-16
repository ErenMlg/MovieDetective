package com.softcross.moviedetective.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.GenreList
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _state = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val state: State<ScreenState<Boolean>> get() = _state

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            contentRepository.getMovieGenres().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _state.value = ScreenState.Error(result.exception.message.toString())
                    }

                    NetworkResponseState.Loading -> {
                        _state.value = ScreenState.Loading
                        delay(500)
                    }

                    is NetworkResponseState.Success -> {
                        _state.value = ScreenState.Success(true)
                        GenreList.setMovieGenreList(result.result)
                    }
                }
            }
        }
    }
}