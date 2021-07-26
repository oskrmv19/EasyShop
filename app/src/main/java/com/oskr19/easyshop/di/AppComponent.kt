package com.oskr19.easyshop.di

import android.content.Context
import androidx.room.Room
import com.oskr19.easyshop.data.core.database.LocalDatabase
import com.oskr19.easyshop.data.core.network.NetworkHandlerImpl
import com.oskr19.easyshop.domain.core.network.INetworkHandler
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
class AppComponent {

    @Provides
    @Singleton
    fun provideNetWorkHandle(context: Context): INetworkHandler {
        return NetworkHandlerImpl(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "local_easy_shop.database"
        ).build()
    }


}