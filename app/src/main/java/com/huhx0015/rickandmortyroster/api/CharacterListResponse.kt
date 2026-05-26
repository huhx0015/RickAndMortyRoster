package com.huhx0015.rickandmortyroster.api

import com.huhx0015.rickandmortyroster.data.RMCharacter
import kotlinx.serialization.Serializable

@Serializable
data class CharacterListResponse(
    val results: List<Result>
)

@Serializable
data class Result(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String
)

fun CharacterListResponse.toCGCharacterList(): List<RMCharacter> {
    val characterList: MutableList<RMCharacter> = mutableListOf()
    this.results.forEach {
        characterList.add(
            RMCharacter(
                id = it.id,
                name = it.name,
                gender = it.gender,
                status = it.status,
                species = it.species,
                image = it.image
            )
        )
    }
    return characterList
}