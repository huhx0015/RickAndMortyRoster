package com.huhx0015.rickandmortyroster.api

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

    @Provides
    fun provideRetrofit(): Retrofit {
        val networkJson = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder().baseUrl("https://rickandmortyapi.com/")
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType())) // should add it at last
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): Huhx0015Api {
        return retrofit.create(Huhx0015Api::class.java)
    }
}