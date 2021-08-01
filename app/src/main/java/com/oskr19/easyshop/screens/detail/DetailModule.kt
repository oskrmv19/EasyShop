package com.oskr19.easyshop.screens.detail

import android.app.Application
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.detail.data.DetailAPI
import com.oskr19.easyshop.screens.detail.data.DetailRepositoryImpl
import com.oskr19.easyshop.screens.detail.domain.DetailRepository
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDescriptionUseCase
import com.oskr19.easyshop.screens.detail.domain.usecase.GetDetailProductUseCase
import com.oskr19.easyshop.screens.detail.presentation.DetailProductUIMapper
import com.oskr19.easyshop.screens.detail.presentation.adapter.PictureAdapter
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
    fun provideAPI(retrofit: Retrofit): DetailAPI {
        return retrofit.create(DetailAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(networkHandler: NetworkHandler, detailAPI: DetailAPI): DetailRepository {
        return DetailRepositoryImpl(networkHandler, detailAPI)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideDetailUseCase(detailRepository: DetailRepository): GetDetailProductUseCase {
        return GetDetailProductUseCase(detailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideDescriptionUseCase(detailRepository: DetailRepository): GetDescriptionUseCase {
        return GetDescriptionUseCase(detailRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideMapper(application: Application): DetailProductUIMapper {
        return DetailProductUIMapper(application)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): PictureAdapter {
        return PictureAdapter()
    }

}