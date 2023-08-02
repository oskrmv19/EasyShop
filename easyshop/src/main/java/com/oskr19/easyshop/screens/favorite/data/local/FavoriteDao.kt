package com.oskr19.easyshop.screens.favorite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by oscar.vergara on 1/08/2021
 */
@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movieId: FavoriteEntity)

    @Query("DELETE FROM favorites where productId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM favorites where productId = :id")
    suspend fun getById(id: String): FavoriteEntity?

    @Query("DELETE FROM favorites")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorites")
    suspend fun getAll(): List<FavoriteEntity>?
}
