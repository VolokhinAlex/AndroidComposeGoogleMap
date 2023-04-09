package com.volokhinaleksey.androidmaps.models

data class Point(
    val id: Long = 0,
    val lat: Double = 0.0,
    val lon: Double = 0.0,
    val title: String = "",
    val description: String = "",
)