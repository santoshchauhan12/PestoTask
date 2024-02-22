package com.android.pestotask

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.pestotask.databinding.ActivityMainBinding
import com.android.pestotask.db.TaskNote
import com.android.pestotask.ext.hide
import com.android.pestotask.ext.show
import com.android.pestotask.ui.AddTaskBottomSheet
import com.android.pestotask.ui.FilterBottomSheet
import com.android.pestotask.ui.TaskStatusBottomSheet
import com.android.pestotask.ui.adapter.TaskAdapter
import com.android.pestotask.utils.Constants
import com.android.pestotask.utils.PreferenceUtils
import com.android.pestotask.viewmodel.MainViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModel()

    private var noteList : MutableList<TaskNote>? = mutableListOf()

    private var taskAdapter : TaskAdapter = TaskAdapter(noteList, itemClick = { taskNote ->
        // Open bottom sheet
       val taskStatusBottomSheet =  TaskStatusBottomSheet.newInstance(taskNote.id.toString())
        taskStatusBottomSheet.show(supportFragmentManager, "TaskStatusBottomSheet")

        taskStatusBottomSheet.setStatusSelectedListeners { taskStatus->
            lifecycleScope.launch {
                taskNote.status = taskStatus.name
                mainViewModel.updateNote(taskNote)
            }
        }

    }, deleteItemClick = { taskToDelete ->

        lifecycleScope.launch {
            taskToDelete.id?.let { mainViewModel.deleteNoteById(it) }
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        setObserver()
        setUiListeners()
        mainViewModel.setUserid(PreferenceUtils.getString(this, Constants.KEY_USERID).orEmpty())
        fetchTaskList()
    }

    private fun setUiListeners() {
        binding.tvFilter.setOnClickListener {

            FilterBottomSheet.newInstance().also {
                it.setFilterSelectedListeners {taskFilter ->
                    mainViewModel.sortByTaskList(taskFilter)
                }
            }.show(supportFragmentManager, "FilterBottomsheet")
        }

        binding.ivFilter.setOnClickListener {
            FilterBottomSheet.newInstance().also {
                it.setFilterSelectedListeners { taskFilter ->
                    mainViewModel.sortByTaskList(taskFilter)
                }
            }.show(supportFragmentManager, "FilterBottomsheet")
        }

        binding.tvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            PreferenceUtils.clearData(this)
            val intent = Intent(
                this,
                LoginActivity::class.java
            )
            startActivity(intent)
            finish()
        }

        binding.fabAddTask.setOnClickListener {
            createAndAddTask()
        }

    }

    private fun initUi() {
        binding.rvTaskList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvTaskList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        binding.rvTaskList.adapter = taskAdapter

        noteList?.let {
            if(it.size == 0) {
                binding.tvNoTask.show()
            }
        }
    }

    /**
     * this is the place where all the list will be observed from database
     */
    private fun setObserver() {
        mainViewModel.listNote.observe(this) { taskList ->
            noteList = taskList

            taskList?.let {
                if(it.size > 0) {
                    taskAdapter.updateList(it)
                    binding.tvNoTask.hide()

                } else {
                    binding.tvNoTask.show()
                }
            }
        }
    }

    private fun fetchTaskList() {
        lifecycleScope.launch {
             mainViewModel.getAllNotes()
        }
    }

    private fun createAndAddTask() {
        val addTaskBottomSheet = AddTaskBottomSheet()
        addTaskBottomSheet.show(supportFragmentManager, "AddTaskBottomSheet")
        addTaskBottomSheet.setAddTaskListener {
            lifecycleScope.launch {
                mainViewModel.insertNote(it)
            }
        }
    }
}