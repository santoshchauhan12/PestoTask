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
//    @Query("SELECT * FROM task_table ORDER BY status DESC")
    suspend fun getAllNotes(userId : String): MutableList<TaskNote>
//    suspend fun getAllNotes(): MutableList<TaskNote>
    // why not use suspend ? because Room does not support LiveData with suspended functions.
    // LiveData already works on a background thread and should be used directly without using coroutines

    @Query("DELETE FROM task_table")
    suspend fun clearTask()

    @Query("DELETE FROM task_table WHERE id = :id AND userid = :userId") //you can use this too, for delete note by id.
//    @Query("DELETE FROM task_table WHERE id = :id") //you can use this too, for delete note by id.

    suspend fun deleteTaskById(id: Int, userId: String) : Int
//    suspend fun deleteTaskById(id: Int) : Int
}