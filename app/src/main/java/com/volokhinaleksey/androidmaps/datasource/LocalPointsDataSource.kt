package com.volokhinaleksey.androidmaps.datasource

import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.room.PointDatabase
import com.volokhinaleksey.androidmaps.room.PointEntity
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

class LocalPointsDataSource(
    private val database: PointDatabase
) : PointsDataSource {

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

    override suspend fun updatePoint(point: Point) {
        database.pointDao().update(mapPointToPointEntity(point = point))
    }

    override suspend fun deletePoint(point: Point) {
        database.pointDao().delete(mapPointToPointEntity(point = point))
    }

    override suspend fun insertPoint(point: Point) {
        database.pointDao().insert(mapPointToPointEntity(point = point))
    }
}

fun mapPointToPointEntity(point: Point): PointEntity = PointEntity(
    id = point.id,
    lat = point.lat,
    lon = point.lon,
    title = point.title,
    description = point.description
)