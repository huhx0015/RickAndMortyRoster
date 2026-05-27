package com.huhx0015.rickandmortyroster.di

import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val BASE_URL = "https://rickandmortyapi.com/"

    @Provides
    fun provideRetrofit(): Retrofit {
        val networkJson = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(networkJson.asConverterFactory(
                "application/json".toMediaType())
            )
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): RickAndMortyApi {
        return retrofit.create(RickAndMortyApi::class.java)
    }
}