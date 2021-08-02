package com.oskr19.easyshop.screens.search

import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.search.data.remote.SearchAPI
import com.oskr19.easyshop.screens.search.data.remote.SearchRepositoryImpl
import com.oskr19.easyshop.screens.search.domain.repository.SearchRepository
import com.oskr19.easyshop.screens.search.domain.usecase.SearchProductUseCase
import com.oskr19.easyshop.screens.search.presentation.adapter.ProductAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.FragmentScoped
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
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideUseCase(searchRepository: SearchRepository): SearchProductUseCase {
        return SearchProductUseCase(searchRepository)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): ProductAdapter {
        return ProductAdapter(arrayListOf())
    }
}