package com.huhx0015.rickandmortyroster.data

import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.huhx0015.rickandmortyroster.api.toRMCharacterList
import com.huhx0015.rickandmortyroster.model.RMCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RickAndMortyRepository(
    private val api: RickAndMortyApi
) {
    private val _characterListStateFlow: MutableStateFlow<List<RMCharacter>> =
        MutableStateFlow(emptyList())
    val characterListStateFlow: StateFlow<List<RMCharacter>> = _characterListStateFlow.asStateFlow()

    suspend fun loadCharacters() {
        val resultList = api.getCharacters().toRMCharacterList()
        updateCharacterList(list = resultList)
    }

    fun getCharacter(id: Int): RMCharacter? {
        return characterListStateFlow.value.firstOrNull { character ->
            character.id == id
        }
    }

    fun getCharacterList(): List<RMCharacter> = _characterListStateFlow.value

    fun isCharacterListEmpty(): Boolean = _characterListStateFlow.value.isEmpty()

    private fun updateCharacterList(list: List<RMCharacter>) {
        _characterListStateFlow.value = list
    }
}