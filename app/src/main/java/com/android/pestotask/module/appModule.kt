package com.android.pestotask.module

import android.app.Application
import androidx.room.Room
import com.android.pestotask.db.TaskDao
import com.android.pestotask.db.TaskDatabase
import com.android.pestotask.repo.ITaskRepository
import com.android.pestotask.repo.TaskRepository
import com.android.pestotask.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Define your dependencies here
    viewModel { MainViewModel(get()) }
}


val repoModule = module {
    single<ITaskRepository> { TaskRepository(get()) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(application, TaskDatabase::class.java, "task_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single { provideDatabase(androidApplication()) }
}
