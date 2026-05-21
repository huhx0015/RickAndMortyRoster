package com.huhx0015.rickandmortyroster.api

import retrofit2.http.GET

/**
 * Api definition -
 *  https://rickandmortyapi.com/documentation/#get-all-characters
 *  https://rickandmortyapi.com/api/character
 *  Data we want to fetch about each character-
 *  id, name, status, species, gender, image
 */

interface Huhx0015Api {
    @GET("api/character")
    suspend fun getCharacters(): CharacterResponse
}