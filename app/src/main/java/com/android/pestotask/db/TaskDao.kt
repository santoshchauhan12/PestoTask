package com.android.pestotask.db

import android.provider.ContactsContract
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if some data is same/conflict, it'll be replace with new data.
    suspend fun insertTask(note: TaskNote)

    @Update
    suspend fun updateTask(note: TaskNote)

    @Delete
    suspend fun deleteTask(note: TaskNote)

    @Query("SELECT * FROM task_table WHERE userid = :userId")
    suspend fun getAllNotes(userId : String): MutableList<TaskNote>

    @Query("DELETE FROM task_table")
    suspend fun clearTask()

    @Query("DELETE FROM task_table WHERE id = :id AND userid = :userId") //you can use this too, for delete note by id.
    suspend fun deleteTaskById(id: Int, userId: String) : Int
}