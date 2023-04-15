package com.volokhinaleksey.androidmaps.repository

import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

/**
 * Interface for getting data from some data source
 */

interface PointsRepository {

    /**
     * Method for getting a list of markers.
     */

    fun getPoints(): Flow<List<Point>>

    /**
     * Method for updating marker data
     * @param point - The marker that needs to be updated
     */

    suspend fun update(point: Point)

    /**
     * Method for deleting marker data
     * @param point - The marker to be deleted
     */

    suspend fun delete(point: Point)

    /**
     * Method for adding new markers
     * @param point - The marker to add
     */

    suspend fun insert(point: Point)

}