package com.softcross.moviedetective.presentation.splash

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.common.CurrentUser
import com.softcross.moviedetective.common.GenreList
import com.softcross.moviedetective.core.common.NetworkResponseState
import com.softcross.moviedetective.core.common.ScreenState
import com.softcross.moviedetective.domain.repository.ContentRepository
import com.softcross.moviedetective.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val contentRepository: ContentRepository,
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {

    private val _movieGenreState = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val movieGenreState: State<ScreenState<Boolean>> get() = _movieGenreState

    private val _seriesGenreState = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val seriesGenreState: State<ScreenState<Boolean>> get() = _seriesGenreState

    private val _userState = mutableStateOf(false)
    val userState: State<Boolean> get() = _userState


    init {
        getMovieGenre()
        getSeriesGenre()
    }

    fun getUserWithID(userID: String) = viewModelScope.launch {
        val user = firebaseRepository.getUserDetailFromFirestore(userID)
        CurrentUser.setCurrentUser(user)
        _userState.value = true
    }

    private fun getMovieGenre() {
        viewModelScope.launch {
            contentRepository.getMovieGenres().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _movieGenreState.value = ScreenState.Error(result.exception.message.toString())
                    }

                    NetworkResponseState.Loading -> {
                        _movieGenreState.value = ScreenState.Loading
                        delay(500)
                    }

                    is NetworkResponseState.Success -> {
                        GenreList.setMovieGenreList(result.result)
                        _movieGenreState.value = ScreenState.Success(true)
                    }
                }
            }
        }
    }

    private fun getSeriesGenre() {
        viewModelScope.launch {
            contentRepository.getSeriesGenres().collect { result ->
                when (result) {
                    is NetworkResponseState.Error -> {
                        _seriesGenreState.value = ScreenState.Error(result.exception.message.toString())
                    }

                    NetworkResponseState.Loading -> {
                        _seriesGenreState.value = ScreenState.Loading
                        delay(500)
                    }

                    is NetworkResponseState.Success -> {
                        GenreList.setMovieGenreList(result.result)
                        _seriesGenreState.value = ScreenState.Success(true)
                    }
                }
            }
        }
    }
}