package com.oskr19.easyshop.screens.lately_seen

import com.oskr19.easyshop.core.data.database.LocalDatabase
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.lately_seen.data.LatelySeenRepositoryImpl
import com.oskr19.easyshop.screens.lately_seen.data.local.LatelySeenDao
import com.oskr19.easyshop.screens.lately_seen.data.remote.LatelySeenAPI
import com.oskr19.easyshop.screens.lately_seen.domain.repository.LatelySeenRepository
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.GetLatelySeenUseCase
import com.oskr19.easyshop.screens.lately_seen.domain.usecase.SaveLatelySeenUseCase
import com.oskr19.easyshop.screens.lately_seen.presentation.adapter.LatelySeenProductAdapter
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
    fun provideAPI(retrofit: Retrofit): LatelySeenAPI {
        return retrofit.create(LatelySeenAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(
        networkHandler: NetworkHandler,
        favoriteDao: FavoriteDao,
        dao: LatelySeenDao,
        api: LatelySeenAPI
    ): LatelySeenRepository {
        return LatelySeenRepositoryImpl(networkHandler, dao, favoriteDao,api)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideDao(database: LocalDatabase): LatelySeenDao {
        return database.latelySeenDao()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideGetFavoritesUseCase(latelySeenRepository: LatelySeenRepository): GetLatelySeenUseCase {
        return GetLatelySeenUseCase(latelySeenRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideSetFavoriteUseCase(latelySeenRepository: LatelySeenRepository): SaveLatelySeenUseCase {
        return SaveLatelySeenUseCase(latelySeenRepository)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): LatelySeenProductAdapter {
        return LatelySeenProductAdapter()
    }
}