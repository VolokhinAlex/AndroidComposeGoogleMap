package com.volokhinaleksey.androidmaps.utils

import com.volokhinaleksey.androidmaps.models.Point
import com.volokhinaleksey.androidmaps.room.PointEntity

fun mapPointToPointEntity(point: Point): PointEntity = PointEntity(
    id = point.id,
    lat = point.lat,
    lon = point.lon,
    title = point.title,
    description = point.description
)