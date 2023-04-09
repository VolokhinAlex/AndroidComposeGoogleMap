package com.volokhinaleksey.androidmaps.repository

import com.volokhinaleksey.androidmaps.models.Point
import kotlinx.coroutines.flow.Flow

interface PointsRepository {

    fun getPoints(): Flow<List<Point>>

    suspend fun update(point: Point)

    suspend fun delete(point: Point)

    suspend fun insert(point: Point)

}