package com.volokhinaleksey.androidmaps.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class PointEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val lat: Double,
    val lon: Double,
    val title: String,
    val description: String,
)
