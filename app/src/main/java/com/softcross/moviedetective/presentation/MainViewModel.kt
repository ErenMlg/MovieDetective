package com.softcross.moviedetective.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.GenreList
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.core.domain.model.Movie
import com.softcross.moviedetective.core.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val contentRepository: ContentRepository
) : ViewModel() {

    private val _mainState = mutableStateOf(false)
    val mainState : State<Boolean> get() = _mainState

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            contentRepository.getMovieGenres().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _mainState.value = false
                        println(result.exception.message)
                    }

                    NetworkResponseState.Loading -> {
                        _mainState.value = false
                    }

                    is NetworkResponseState.Success -> {
                        _mainState.value = true
                        GenreList.setMovieGenreList(result.result)
                    }
                }
            }
        }
    }

}