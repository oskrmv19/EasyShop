package com.oskr19.easyshop.screens.favorite

import com.oskr19.easyshop.core.data.database.LocalDatabase
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.favorite.data.FavoriteRepositoryImpl
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.favorite.data.remote.FavoriteAPI
import com.oskr19.easyshop.screens.favorite.domain.repository.FavoriteRepository
import com.oskr19.easyshop.screens.favorite.domain.usecase.GetFavoritesUseCase
import com.oskr19.easyshop.screens.favorite.domain.usecase.RemoveAllFavoritesUseCase
import com.oskr19.easyshop.screens.favorite.domain.usecase.SetFavoriteUseCase
import com.oskr19.easyshop.screens.favorite.presentation.adapter.FavoriteProductAdapter
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
 * Created by oscar.vergara on 01/08/2021
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
class DataModule {

    @Provides
    @ActivityRetainedScoped
    fun provideAPI(retrofit: Retrofit): FavoriteAPI {
        return retrofit.create(FavoriteAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(
        networkHandler: NetworkHandler,
        dao: FavoriteDao,
        api: FavoriteAPI
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(networkHandler, dao, api)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDao(database: LocalDatabase): FavoriteDao {
        return database.favoriteDao()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetFavoritesUseCase(favoriteRepository: FavoriteRepository): GetFavoritesUseCase {
        return GetFavoritesUseCase(favoriteRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSetFavoriteUseCase(favoriteRepository: FavoriteRepository): SetFavoriteUseCase {
        return SetFavoriteUseCase(favoriteRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideRemoveAllFavoritesUseCase(favoriteRepository: FavoriteRepository): RemoveAllFavoritesUseCase {
        return RemoveAllFavoritesUseCase(favoriteRepository)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): FavoriteProductAdapter {
        return FavoriteProductAdapter()
    }
}