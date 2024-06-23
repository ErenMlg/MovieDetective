package com.softcross.moviedetective.presentation.home.popularPeoples

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.softcross.moviedetective.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PopularPeoplesViewModel @Inject constructor(
    contentRepository: ContentRepository
): ViewModel() {

    val popularPeoplesPagingFlow = contentRepository.getPopularActorsByPage()
        .flow
        .cachedIn(viewModelScope)

}