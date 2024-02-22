package com.android.pestotask.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pestotask.db.TaskNote
import com.android.pestotask.model.TaskFilters
import com.android.pestotask.model.TaskStatus
import com.android.pestotask.repo.ITaskRepository
import com.android.pestotask.repo.TaskRepository
import kotlinx.coroutines.flow.*

class MainViewModel(private val taskRepository: ITaskRepository): ViewModel() {

    private var _listNote = MutableLiveData<MutableList<TaskNote>?>()
    var listNote : LiveData<MutableList<TaskNote>?> = _listNote

    private var userId: String? = null

    fun setUserid(uId: String) {
        userId = uId
    }

    fun getUserId(): String? {
        return userId
    }
    suspend fun insertNote(note: TaskNote)  {
        taskRepository.insertNote(note)
        getAllNotes()
    }

    suspend fun updateNote(note: TaskNote) {
        taskRepository.updateNote(note)
        getAllNotes()
    }

    suspend fun deleteNote(note: TaskNote) {
        taskRepository.deleteNote(note)
    }

    suspend fun deleteNoteById(id: Int) {
        val numDeleted = taskRepository.deleteNoteById(id, userId.orEmpty())
        if (numDeleted.toInt() > 0) {
            val modifyList = listNote.value
            val list  = modifyList?.let { list ->
                list.filter { it.id != id } as MutableList<TaskNote>
            }
            _listNote.postValue(list)
        }
    }

    suspend fun clearNote() {
        taskRepository.clearNote()
    }

    suspend fun getAllNotes() {
        _listNote.postValue(taskRepository.getAllNotes(userId.orEmpty()))
    }

    fun filterTaskList(taskFilter: TaskFilters) {
        when(taskFilter) {
            TaskFilters.BYTITLE -> {
                val modifyList = listNote.value
                val list = modifyList?.sortedBy { it.title } as MutableList<TaskNote>
                _listNote.postValue(list)
            }

            TaskFilters.BYDATE -> {
                val modifyList = listNote.value
                val list = modifyList?.sortedBy { it.createdTime } as MutableList<TaskNote>
                _listNote.postValue(list)
            }
        }
    }
}