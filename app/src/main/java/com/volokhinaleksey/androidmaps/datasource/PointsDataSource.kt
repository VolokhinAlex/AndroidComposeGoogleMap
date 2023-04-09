package com.volokhinaleksey.androidmaps.datasource

import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

interface PointsDataSource {

    fun getPoints(): Flow<List<Point>>

    suspend fun updatePoint(point: Point)

    suspend fun deletePoint(point: Point)

    suspend fun insertPoint(point: Point)
}