package com.airlinesdemoapp.core.di

import com.airlinesdemoapp.data.api.RestAPI
import com.airlinesdemoapp.data.repository.AirlinesRepositoryImpl
import com.airlinesdemoapp.domain.repository.AirlinesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {
    @Singleton
    @Provides
    fun providesRestaurantsRepository(api: RestAPI): AirlinesRepository {
        return AirlinesRepositoryImpl(api)
    }
}