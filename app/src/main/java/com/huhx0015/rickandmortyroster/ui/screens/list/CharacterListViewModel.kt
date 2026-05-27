package com.huhx0015.rickandmortyroster.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.huhx0015.rickandmortyroster.api.toCGCharacterList
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
    val api: RickAndMortyApi
) : ViewModel() {

    private val _state: MutableStateFlow<CharacterListState> = MutableStateFlow(
        CharacterListState()
    )
    val state: StateFlow<CharacterListState> = _state.asStateFlow()

    init {
        initData()
    }

    fun initData() {
        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch(Dispatchers.IO) {
            val characterList = api.getCharacters().toCGCharacterList()
            _state.update { state ->
                state.copy(
                    characterList = characterList,
                    isLoading = false
                )
            }
        }
    }
}