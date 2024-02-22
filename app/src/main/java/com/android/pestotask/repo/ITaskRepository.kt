package com.android.pestotask.repo

import com.android.pestotask.db.TaskNote

interface ITaskRepository {
    suspend fun insertNote(note: TaskNote)

    suspend fun updateNote(note: TaskNote)

    suspend fun deleteNote(note: TaskNote)

    suspend fun deleteNoteById(id: Int, userId: String): Int

    suspend fun clearNote()

    suspend fun getAllNotes(userId: String): MutableList<TaskNote>
}