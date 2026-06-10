package com.huhx0015.rickandmortyroster.di

import com.huhx0015.rickandmortyroster.api.RickAndMortyApi
import com.huhx0015.rickandmortyroster.data.RickAndMortyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideCharacterListRepository(api: RickAndMortyApi): RickAndMortyRepository {
        return RickAndMortyRepository(api)
    }
}
