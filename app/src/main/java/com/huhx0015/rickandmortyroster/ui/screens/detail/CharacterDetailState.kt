package com.huhx0015.rickandmortyroster.ui.screens.detail

import com.huhx0015.rickandmortyroster.model.RMCharacter

data class CharacterDetailState(
    val characterId: Int? = null,
    val character: RMCharacter? = null,
    val isError: Boolean = false,
    val isLoading: Boolean = false
)