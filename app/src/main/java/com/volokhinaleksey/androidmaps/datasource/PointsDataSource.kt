package com.volokhinaleksey.androidmaps.datasource

import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

/**
 * Abstraction for getting marker data from some data source
 */

interface PointsDataSource {

    /**
     * Method for getting a list of markers from a data source
     */

    fun getPoints(): Flow<List<Point>>

    /**
     * Method for updating marker data
     * @param point - The marker that needs to be updated
     */

    suspend fun updatePoint(point: Point)

    /**
     * Method for deleting marker data
     * @param point - The marker to be deleted
     */

    suspend fun deletePoint(point: Point)

    /**
     * Method for adding new markers
     * @param point - The marker to add
     */

    suspend fun insertPoint(point: Point)
}