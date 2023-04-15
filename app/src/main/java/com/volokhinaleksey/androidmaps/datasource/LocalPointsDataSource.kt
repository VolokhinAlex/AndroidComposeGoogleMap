package com.volokhinaleksey.androidmaps.datasource

import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.room.PointDatabase
import com.volokhinaleksey.androidmaps.utils.mapPointToPointEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

/**
 * Implementation of an interface for getting data from a local data source
 * @param database - Local database.
 */

class LocalPointsDataSource(
    private val database: PointDatabase
) : PointsDataSource {

    /**
     * Method for getting a list of markers from a data source
     */

    @OptIn(FlowPreview::class)
    override fun getPoints(): Flow<List<Point>> {
        return database.pointDao().all().flatMapConcat {
            flowOf(it.map { point ->
                Point(
                    id = point.id,
                    lat = point.lat,
                    lon = point.lon,
                    title = point.title,
                    description = point.description
                )
            })
        }
    }

    /**
     * Method for updating marker data
     * @param point - The marker that needs to be updated
     */

    override suspend fun updatePoint(point: Point) {
        database.pointDao().update(mapPointToPointEntity(point = point))
    }

    /**
     * Method for deleting marker data
     * @param point - The marker to be deleted
     */

    override suspend fun deletePoint(point: Point) {
        database.pointDao().delete(mapPointToPointEntity(point = point))
    }

    /**
     * Method for adding new markers
     * @param point - The marker to add
     */

    override suspend fun insertPoint(point: Point) {
        database.pointDao().insert(mapPointToPointEntity(point = point))
    }
}