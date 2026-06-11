package com.huhx0015.rickandmortyroster.api

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Rick and Morty API
 * Source: https://rickandmortyapi.com/api/
 */

interface RickAndMortyApi {
    @GET("api/character")
    suspend fun getCharacters(@Query("page") page: Int = 1): CharacterListResponse
}