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

    private val _state = mutableStateOf<ScreenState<Boolean>>(ScreenState.Loading)
    val state: State<ScreenState<Boolean>> get() = _state


    private val _userState = mutableStateOf(false)
    val userState: State<Boolean> get() = _userState


    init {
        getCategories()
    }

    fun getUserWithID(userID: String) = viewModelScope.launch {
        val user = firebaseRepository.getUserDetailFromFirestore(userID)
        CurrentUser.setCurrentUser(user)
        _userState.value = true
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
                        GenreList.setMovieGenreList(result.result)
                        _state.value = ScreenState.Success(true)
                    }
                }
            }
        }
    }
}