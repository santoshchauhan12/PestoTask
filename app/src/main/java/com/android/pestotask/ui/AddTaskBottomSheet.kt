package com.android.pestotask.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import com.android.pestotask.R
import com.android.pestotask.databinding.FragmentAddTaskBottomSheetBinding
import com.android.pestotask.databinding.FragmentFilterBottomSheetBinding
import com.android.pestotask.db.TaskNote
import com.android.pestotask.model.TaskFilters
import com.android.pestotask.model.TaskStatus
import com.android.pestotask.utils.Constants
import com.android.pestotask.utils.PreferenceUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class AddTaskBottomSheet  : BottomSheetDialogFragment() {

    lateinit var binding: FragmentAddTaskBottomSheetBinding

    private var addTaskListener: ((TaskNote)-> Unit)?= null

    fun setAddTaskListener(callback: ((TaskNote)-> Unit)?) {
        addTaskListener = callback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.btnAdd.setOnClickListener {

            val desc = binding.etAddDesc.text.toString()
            val title = binding.etTitle.text.toString()

            if(title.isEmpty()) {
                Toast.makeText(this.requireContext(), "Title is empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(desc.isEmpty()) {
                Toast.makeText(this.requireContext(), "Description is empty", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            val task = TaskNote(
                id = Random.nextInt(0, Int.MAX_VALUE),
                userid = PreferenceUtils.getString(this.requireContext(), Constants.KEY_USERID).orEmpty(),
                title = title,
                description =  desc,
                status = TaskStatus.TODO.name,
                createdTime = System.currentTimeMillis()
            )
            addTaskListener?.invoke(task)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilterBottomSheet()
    }
}