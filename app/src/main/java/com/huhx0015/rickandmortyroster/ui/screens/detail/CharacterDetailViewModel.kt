package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.huhx0015.rickandmortyroster.data.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    val characterRepository: CharacterRepository
): ViewModel() {

    val _stateFlow: MutableStateFlow<CharacterDetailState> = MutableStateFlow(
        CharacterDetailState()
    )
    val stateFlow: StateFlow<CharacterDetailState> = _stateFlow.asStateFlow()

    init {

    }
}