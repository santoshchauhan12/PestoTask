package com.android.pestotask

import android.app.Application
import com.android.pestotask.module.appModule
import com.android.pestotask.module.databaseModule
import com.android.pestotask.module.repoModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class TaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // Start Koin with the provided module
        startKoin {
            androidContext(this@TaskApp)
            modules(appModule, repoModule, databaseModule)
        }
    }
}