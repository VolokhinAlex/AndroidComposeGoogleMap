package com.volokhinaleksey.androidmaps.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * An object for managing a local database
 */

@Dao
interface PointDao {

    /**
     * Method for inserting a marker into a local database
     * @param point - The marker that needs to be inserted.
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(point: PointEntity)

    /**
     * Method for updating a marker in a local database
     * @param point - The token that needs to be updated.
     */

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(point: PointEntity)

    /**
     * Method for deleting a marker from a local database
     * @param point - The marker that needs to be deleted.
     */

    @Delete
    suspend fun delete(point: PointEntity)

    /**
     * Method for getting a list of markers from the database.
     */

    @Query("SELECT * FROM points")
    fun all(): Flow<List<PointEntity>>
}