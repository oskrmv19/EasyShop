package com.oskr19.easyshop.screens.search

import android.app.Application
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.search.data.remote.SearchAPI
import com.oskr19.easyshop.screens.search.data.remote.SearchRepositoryImpl
import com.oskr19.easyshop.screens.search.domain.repository.SearchRepository
import com.oskr19.easyshop.screens.search.domain.usecase.SearchProductUseCase
import com.oskr19.easyshop.screens.search.presentation.adapter.ProductAdapter
import com.oskr19.easyshop.screens.search.presentation.mapper.ProductUIMapper
import com.oskr19.easyshop.screens.search.presentation.model.ProductUI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit

/**
 * Created by oscar.vergara on 26/07/2021
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAdapter(retrofit: Retrofit): ProductAdapter {
        return ProductAdapter(arrayListOf())
    }

    @Provides
    @ActivityRetainedScoped
    fun provideMapper(application: Application): ProductUIMapper {
        return ProductUIMapper(application)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideAPI(retrofit: Retrofit): SearchAPI {
        return retrofit.create(SearchAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(networkHandler: NetworkHandler, prefs: EasyShopPrefs, searchAPI: SearchAPI): SearchRepository {
        return SearchRepositoryImpl(networkHandler, prefs, searchAPI)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class PresentationModule {

    @Provides
    @ViewModelScoped
    fun provideUseCase(searchRepository: SearchRepository): SearchProductUseCase {
        return SearchProductUseCase(searchRepository)
    }
}