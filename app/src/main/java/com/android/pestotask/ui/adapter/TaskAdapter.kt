package com.android.pestotask.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.pestotask.databinding.ViewItemTaskBinding
import com.android.pestotask.db.TaskNote

class TaskAdapter(private var tasks: MutableList<TaskNote>?,
                  private var itemClick: (TaskNote) -> Unit,
                    private var deleteItemClick: (TaskNote) -> Unit
): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    fun updateList(list: MutableList<TaskNote>?) {
        tasks?.clear()
        if (list != null) {
            this.tasks?.addAll(list)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        return TaskViewHolder(ViewItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks?.get(position)
        if (task != null) {
            holder.bind(task)
        }
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 0
    }

    inner class TaskViewHolder(private val  binding: ViewItemTaskBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(task: TaskNote) {
            binding.tvTitle.text = task.title
            binding.tvDesc.text = task.description
            binding.tvStatus.text = task.status
            binding.ivEditStatus.setOnClickListener {
                itemClick.invoke(task)
            }

            binding.btnDelete.setOnClickListener {
                deleteItemClick.invoke(task)
            }
        }
    }
}