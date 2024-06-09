package com.softcross.moviedetective.presentation.signup

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softcross.moviedetective.core.common.Resource
import com.softcross.moviedetective.core.domain.model.User
import com.softcross.moviedetective.core.domain.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseRepo: FirebaseRepository,
) : ViewModel() {

    private val _registerUiState = mutableStateOf(RegisterUiState())
    val registerUiState: State<RegisterUiState> = _registerUiState

    fun registerUser(email: String, password: String, name: String, surname: String) =
        viewModelScope.launch {
            firebaseRepo.registerUser(email, password, name, surname).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _registerUiState.value = RegisterUiState(errorMessage = result.errorMessage)
                    }

                    is Resource.Success -> {
                        _registerUiState.value = RegisterUiState(data = result.data)
                    }

                    is Resource.Loading -> {
                        _registerUiState.value = RegisterUiState(isLoading = true)
                    }
                }
            }
        }

}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val data: User? = null
)