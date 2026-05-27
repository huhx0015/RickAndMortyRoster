package com.huhx0015.rickandmortyroster.ui.screens.list

import com.huhx0015.rickandmortyroster.data.RMCharacter

data class CharacterListState(
    val characterList: List<RMCharacter> = emptyList()
)