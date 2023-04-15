package com.volokhinaleksey.androidmaps.repository

import com.volokhinaleksey.androidmaps.datasource.PointsDataSource
import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of the repository interface for getting data from a data source
 */

class LocalPointsRepository(
    private val dataSource: PointsDataSource
) : PointsRepository {

    /**
     * Method for getting a list of markers from the data source
     */

    override fun getPoints(): Flow<List<Point>> = dataSource.getPoints()

    /**
     * Method for updating marker data
     * @param point - The marker that needs to be updated
     */

    override suspend fun update(point: Point) {
        dataSource.updatePoint(point = point)
    }

    /**
     * Method for deleting marker data from the data source
     * @param point - The marker to be deleted
     */

    override suspend fun delete(point: Point) {
        dataSource.deletePoint(point = point)
    }

    /**
     * Method for adding new markers to the data source
     * @param point - The marker to add
     */

    override suspend fun insert(point: Point) {
        dataSource.insertPoint(point = point)
    }

}