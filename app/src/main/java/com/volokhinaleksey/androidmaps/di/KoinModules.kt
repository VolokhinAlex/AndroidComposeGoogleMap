package com.volokhinaleksey.androidmaps.di

import androidx.room.Room
import com.volokhinaleksey.androidmaps.datasource.LocalPointsDataSource
import com.volokhinaleksey.androidmaps.datasource.PointsDataSource
import com.volokhinaleksey.androidmaps.repository.LocalPointsRepository
import com.volokhinaleksey.androidmaps.repository.PointsRepository
import com.volokhinaleksey.androidmaps.room.PointDatabase
import com.volokhinaleksey.androidmaps.viewmodel.PointViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val DATABASE = "points.db"

val database = module {
    single {
        Room.databaseBuilder(get(), PointDatabase::class.java, DATABASE).build()
    }
}

val homeScreen = module {
    factory<PointsDataSource> { LocalPointsDataSource(get()) }
    factory<PointsRepository> { LocalPointsRepository(get()) }
    viewModel { PointViewModel(get()) }
}