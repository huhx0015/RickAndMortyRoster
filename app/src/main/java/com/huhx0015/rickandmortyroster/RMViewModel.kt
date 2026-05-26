package com.huhx0015.rickandmortyroster

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
class RMViewModel @Inject constructor(
    val api: RickAndMortyApi
) : ViewModel() {

    private val _state: MutableStateFlow<RMState> = MutableStateFlow(RMState())
    val state: StateFlow<RMState> = _state.asStateFlow()

    init {
        initData()
    }

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            val characterList = api.getCharacters().toCGCharacterList()
            _state.update { state ->
                state.copy(
                    characterList = characterList
                )
            }
        }
    }
}

