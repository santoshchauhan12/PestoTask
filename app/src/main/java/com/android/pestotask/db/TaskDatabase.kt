package com.android.pestotask.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [TaskNote::class],
    version = 3,
    exportSchema = false
)

abstract class TaskDatabase: RoomDatabase() {
    abstract fun getTaskDao(): TaskDao
}