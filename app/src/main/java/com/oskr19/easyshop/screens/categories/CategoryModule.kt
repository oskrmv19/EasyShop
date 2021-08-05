package com.oskr19.easyshop.screens.categories

import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import com.oskr19.easyshop.screens.categories.data.CategoriesAPI
import com.oskr19.easyshop.screens.categories.data.CategoryRepositoryImpl
import com.oskr19.easyshop.screens.categories.domain.CategoryRepository
import com.oskr19.easyshop.screens.categories.domain.usecase.GetCategoriesUseCase
import com.oskr19.easyshop.screens.categories.domain.usecase.GetCategoryInfoUseCase
import com.oskr19.easyshop.screens.categories.presentation.adapter.CategoryAdapter
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
    fun provideAPI(retrofit: Retrofit): CategoriesAPI {
        return retrofit.create(CategoriesAPI::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideRepository(networkHandler: NetworkHandler, categoriesAPI: CategoriesAPI, prefs: EasyShopPrefs): CategoryRepository {
        return CategoryRepositoryImpl(networkHandler, prefs,categoriesAPI)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    @ViewModelScoped
    fun provideCategoriesUseCase(categoryRepository: CategoryRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(categoryRepository)
    }

    @Provides
    @ViewModelScoped
    fun provideCategoryInfoUseCase(categoryRepository: CategoryRepository): GetCategoryInfoUseCase {
        return GetCategoryInfoUseCase(categoryRepository)
    }
}

@Module
@InstallIn(FragmentComponent::class)
class PresentationModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(): CategoryAdapter {
        return CategoryAdapter()
    }

}