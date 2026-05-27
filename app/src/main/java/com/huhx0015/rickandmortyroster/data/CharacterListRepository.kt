package com.huhx0015.rickandmortyroster.data

import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.huhx0015.rickandmortyroster.api.toRMCharacterList
import com.huhx0015.rickandmortyroster.model.RMCharacter

class CharacterListRepository(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacters(): List<RMCharacter> {
        return api.getCharacters().toRMCharacterList()
    }
}