package com.oskr19.easyshop.data.core.database

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Created by oscar.vergara on 25/07/2021
 */
@Database(
    version = 1, exportSchema = false,
    entities = [ProductEntity::class]
)
abstract class LocalDatabase: RoomDatabase() {
    //Dao
}