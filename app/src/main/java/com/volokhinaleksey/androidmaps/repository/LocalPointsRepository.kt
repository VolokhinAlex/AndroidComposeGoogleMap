package com.volokhinaleksey.androidmaps.repository

import com.volokhinaleksey.androidmaps.datasource.PointsDataSource
import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

class LocalPointsRepository(
    private val dataSource: PointsDataSource
) : PointsRepository {

    override fun getPoints(): Flow<List<Point>> = dataSource.getPoints()

    override suspend fun update(point: Point) {
        dataSource.updatePoint(point = point)
    }

    override suspend fun delete(point: Point) {
        dataSource.deletePoint(point = point)
    }

    override suspend fun insert(point: Point) {
        dataSource.insertPoint(point = point)
    }

}