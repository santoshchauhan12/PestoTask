package com.android.pestotask.repo

import androidx.lifecycle.LiveData
import com.android.pestotask.db.TaskDatabase
import com.android.pestotask.db.TaskNote

class TaskRepository(
    private val noteDatabase: TaskDatabase
) : ITaskRepository{

    override suspend fun insertNote(note: TaskNote) = noteDatabase.getTaskDao().insertTask(note)

    override suspend fun updateNote(note: TaskNote) = noteDatabase.getTaskDao().updateTask(note)

    override suspend fun deleteNote(note: TaskNote) = noteDatabase.getTaskDao().deleteTask(note)

    override suspend fun deleteNoteById(id: Int, userId: String): Int {

        return  noteDatabase.getTaskDao().deleteTaskById(id, userId)

    }

    override suspend fun clearNote() = noteDatabase.getTaskDao().clearTask()

    override suspend fun getAllNotes(userId: String): MutableList<TaskNote> = noteDatabase.getTaskDao().getAllNotes(userId)
}