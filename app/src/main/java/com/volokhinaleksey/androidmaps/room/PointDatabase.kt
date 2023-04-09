package com.volokhinaleksey.androidmaps.room

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * Local Database object
 */

@Database(
    entities = [PointEntity::class],
    version = 1
)
abstract class PointDatabase : RoomDatabase() {

    abstract fun pointDao(): PointDao

}