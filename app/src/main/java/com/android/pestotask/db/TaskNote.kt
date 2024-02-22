package com.android.pestotask.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskNote (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")var id: Int?,
    @ColumnInfo(name = "userid")var userid: String,
    @ColumnInfo(name = "title")var title: String?,
    @ColumnInfo(name = "description")var description: String?,
    @ColumnInfo(name = "status")var status: String?,
    @ColumnInfo(name = "createdTime")var createdTime: Long?
) : java.io.Serializable