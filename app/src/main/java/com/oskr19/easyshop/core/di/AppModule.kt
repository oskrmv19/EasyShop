package com.oskr19.easyshop.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.oskr19.easyshop.core.data.database.LocalDatabase
import com.oskr19.easyshop.core.data.network.NetworkHandlerImpl
import com.oskr19.easyshop.core.data.preferences.EasyShopPrefs
import com.oskr19.easyshop.core.domain.network.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by oscar.vergara on 25/07/2021
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideNetWorkHandler(application: Application): NetworkHandler {
        return NetworkHandlerImpl(application)
    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): LocalDatabase {
        return Room.databaseBuilder(
            application,
            LocalDatabase::class.java,
            "local_easy_shop.database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideEasyShopPrefs(application: Application): EasyShopPrefs {
        return EasyShopPrefs(application)
    }

}