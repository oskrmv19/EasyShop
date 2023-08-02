package com.oskr19.easyshop.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteDao
import com.oskr19.easyshop.screens.favorite.data.local.FavoriteEntity
import com.oskr19.easyshop.screens.lately_seen.data.local.LatelySeenDao
import com.oskr19.easyshop.screens.lately_seen.data.local.LatelySeenEntity

/**
 * Created by oscar.vergara on 25/07/2021
 */
@Database(
    version = 1, exportSchema = false,
    entities = [FavoriteEntity::class, LatelySeenEntity::class]
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
    abstract fun latelySeenDao(): LatelySeenDao
}
