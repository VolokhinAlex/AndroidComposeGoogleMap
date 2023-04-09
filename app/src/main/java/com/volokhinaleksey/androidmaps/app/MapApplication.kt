package com.volokhinaleksey.androidmaps.app

import android.app.Application
import com.volokhinaleksey.androidmaps.di.database
import com.volokhinaleksey.androidmaps.di.homeScreen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MapApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MapApplication)
            modules(listOf(database, homeScreen))
        }
    }

}