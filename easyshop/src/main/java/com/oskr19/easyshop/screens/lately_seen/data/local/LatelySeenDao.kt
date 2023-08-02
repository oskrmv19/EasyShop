package com.oskr19.easyshop.screens.lately_seen.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by oscar.vergara on 1/08/2021
 */
@Dao
interface LatelySeenDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieId: LatelySeenEntity)

    @Query("DELETE FROM lately_seen where productId = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT * FROM lately_seen where productId = :id")
    suspend fun getById(id: String): LatelySeenEntity?

    @Query("SELECT * FROM lately_seen")
    suspend fun getAll(): List<LatelySeenEntity>?
}
