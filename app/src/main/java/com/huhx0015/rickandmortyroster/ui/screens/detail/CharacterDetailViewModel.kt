package com.huhx0015.rickandmortyroster.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huhx0015.rickandmortyroster.data.RickAndMortyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    val rickAndMortyRepository: RickAndMortyRepository
): ViewModel() {

    private val _state: MutableStateFlow<CharacterDetailState> = MutableStateFlow(
        CharacterDetailState()
    )
    val state: StateFlow<CharacterDetailState> = _state.asStateFlow()

    init {

    }

    fun loadCharacterData(id: Int) {
        _state.update { it.copy(isLoading = true, isError = false) }

        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                rickAndMortyRepository.getCharacter(id)
            }.onSuccess { character ->
                _state.update { state ->
                    state.copy(
                        character = character,
                        isLoading = false,
                        isError = false
                    )
                }
            }.onFailure {
                _state.update { state ->
                    state.copy(
                        isLoading = false,
                        isError = true
                    )
                }
            }
        }
    }
}