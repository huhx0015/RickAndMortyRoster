package com.huhx0015.rickandmortyroster.ui.screens.list

import com.huhx0015.rickandmortyroster.model.RMCharacter

data class CharacterListState(
    val characterList: List<RMCharacter> = emptyList(),
    val currentPage: Int = 1,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isLoadingMore: Boolean = false
)