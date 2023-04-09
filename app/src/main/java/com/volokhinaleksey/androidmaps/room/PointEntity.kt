package com.volokhinaleksey.androidmaps.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * The entity for storing the token in the database.
 *
 * @param id - Marker ID
 * @param lat - Latitude is the coordinate that determines the position of a point from north to south
 * @param lon - Longitude is the measurement east or west of the prime meridian.
 * @param title - Address name
 * @param description - Specific address description
 *
 */

@Entity(tableName = "points")
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val lat: Double,
    val lon: Double,
    val title: String,
    val description: String,
)
