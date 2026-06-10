package com.huhx0015.rickandmortyroster.ui.screens.list

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
class CharacterListViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    private val _state: MutableStateFlow<CharacterListState> = MutableStateFlow(
        CharacterListState()
    )
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        initData()
    }

    fun initData() {
        _state.update { it.copy(isLoading = true, isError = false) }

        if (repository.isCharacterListEmpty()) {
            loadData()
        } else {
            _state.update { state ->
                state.copy(
                    characterList = repository.getCharacterList(),
                    isLoading = false,
                    isError = false
                )
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.loadCharacters()
            }.onSuccess { characterList ->
                _state.update { state ->
                    state.copy(
                        characterList = characterList,
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