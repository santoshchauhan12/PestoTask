package com.android.pestotask.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.pestotask.R
import com.android.pestotask.databinding.FragmentFilterBottomSheetBinding
import com.android.pestotask.databinding.FragmentTaskStatusBottomSheetBinding
import com.android.pestotask.model.TaskFilters
import com.android.pestotask.model.TaskStatus
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

private const val TASK_ID = "task_id"
class TaskStatusBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentTaskStatusBottomSheetBinding

    private var statusSelectedListener: ((TaskStatus) -> Unit)? = null

    fun setStatusSelectedListeners(callBack: ((TaskStatus) -> Unit)?) {
        statusSelectedListener = callBack
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskStatusBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
    }

    private fun initUi() {
        binding.tvStatusTodo.setOnClickListener {
            statusSelectedListener?.invoke(TaskStatus.TODO)
            dismiss()
        }

        binding.tvStatusDone
            .setOnClickListener {
            statusSelectedListener?.invoke(TaskStatus.DONE)
                dismiss()

            }

        binding.tvStatusInprogress
            .setOnClickListener {
                statusSelectedListener?.invoke(TaskStatus.INPROGRESS)
                dismiss()

            }

        binding.tvStatusNotdo
            .setOnClickListener {
                statusSelectedListener?.invoke(TaskStatus.NOTDO)
                dismiss()

            }
    }

    companion object {
        @JvmStatic
        fun newInstance(id: String): TaskStatusBottomSheet  {
            val fragment = TaskStatusBottomSheet()
//            val bundle = Bundle()
//            bundle.putString(TASK_ID, id)
//            fragment.arguments = bundle

            return fragment
        }
    }
}