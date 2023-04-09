package com.volokhinaleksey.androidmaps.models

/**
 * The data class for storing the marker
 *
 * @param id - Marker ID
 * @param lat - Latitude is the coordinate that determines the position of a point from north to south
 * @param lon - Longitude is the measurement east or west of the prime meridian.
 * @param title - Address name
 * @param description - Specific address description
 *
 */

data class Point(
    val id: Long = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val title: String = "",
    val description: String = "",
)