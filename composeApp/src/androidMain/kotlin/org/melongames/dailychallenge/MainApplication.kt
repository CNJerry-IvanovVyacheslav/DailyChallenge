package org.melongames.dailychallenge

import DatabaseDriverFactory
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.dsl.module
import org.melongames.dailychallenge.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidLogger()
            androidContext(this@MainApplication)

            modules(module {
                single { DatabaseDriverFactory(context = get()) }
            })
        }
    }
}