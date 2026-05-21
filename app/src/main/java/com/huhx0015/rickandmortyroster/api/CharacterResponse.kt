package com.huhx0015.rickandmortyroster.api

import com.huhx0015.rickandmortyroster.data.RMCharacter
import kotlinx.serialization.Serializable

//{
//    "info": {
//    "count": 826,
//    "pages": 42,
//    "next": "https://rickandmortyapi.com/api/character/?page=2",
//    "prev": null
//},
//    "results": [
//    {
//        "id": 1,
//        "name": "Rick Sanchez",
//        "status": "Alive",
//        "species": "Human",
//        "type": "",
//        "gender": "Male",
//        "origin": {
//        "name": "Earth",
//        "url": "https://rickandmortyapi.com/api/location/1"
//    },
//        "location": {
//        "name": "Earth",
//        "url": "https://rickandmortyapi.com/api/location/20"
//    },
//        "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
//        "episode": [
//        "https://rickandmortyapi.com/api/episode/1",
//        "https://rickandmortyapi.com/api/episode/2",
//        // ...
//        ],
//        "url": "https://rickandmortyapi.com/api/character/1",
//        "created": "2017-11-04T18:48:46.250Z"
//    },
//    // ...
//    ]
//}

// id, name, status, species, gender, image
@Serializable
data class CharacterResponse(
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

fun CharacterResponse.toCGCharacterList(): List<RMCharacter> {
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