package com.softcross.moviedetective.presentation.signin

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.Resource
import com.softcross.moviedetective.domain.model.User
import com.softcross.moviedetective.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseRepo: FirebaseRepository,
) : ViewModel() {

    private val _loginUiState = mutableStateOf(LoginUiState())
    val loginUiState: State<LoginUiState> = _loginUiState


    fun loginUser(email: String, password: String) = viewModelScope.launch {
        firebaseRepo.loginUser(email, password).collect { result ->
            when (result) {
                is Resource.Loading -> {
                    _loginUiState.value = LoginUiState(isLoading = true)
                }

                is Resource.Error -> {
                    _loginUiState.value = LoginUiState(errorMessage = result.errorMessage)
                }

                is Resource.Success -> {
                    _loginUiState.value = LoginUiState(user = result.data, isLoading = true)
                }
            }
        }
    }

}

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null
)