package com.volokhinaleksey.androidmaps.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PointDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(point: PointEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(point: PointEntity)

    @Delete
    suspend fun delete(point: PointEntity)

    @Query("SELECT * FROM points")
    fun all(): Flow<List<PointEntity>>
}