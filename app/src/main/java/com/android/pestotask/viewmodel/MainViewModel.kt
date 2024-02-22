package com.android.pestotask.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.pestotask.db.TaskNote
import com.android.pestotask.model.TaskFilters
import com.android.pestotask.repo.ITaskRepository

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

    /**
     * delete a task from the list based on id
     */
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

    /**
     * fetch all the task from database
     */
    suspend fun getAllNotes() {
        _listNote.postValue(taskRepository.getAllNotes(userId.orEmpty()))
    }

    /**
     * sort the list based on title or creation time
     */
    fun sortByTaskList(taskFilter: TaskFilters) {
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