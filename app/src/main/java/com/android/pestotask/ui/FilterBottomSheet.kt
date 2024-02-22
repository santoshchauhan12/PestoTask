package com.android.pestotask.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.pestotask.R
import com.android.pestotask.databinding.FragmentFilterBottomSheetBinding
import com.android.pestotask.model.TaskFilters
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomSheet : BottomSheetDialogFragment() {

    lateinit var binding: FragmentFilterBottomSheetBinding

    private var filterSelected: ((TaskFilters)-> Unit)?= null

    fun setFilterSelectedListeners(callBack: ((TaskFilters)-> Unit)?) {
        filterSelected = callBack
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
        binding = FragmentFilterBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
    }

    private fun initUi() {
        binding.tvSortDate.setOnClickListener {
            filterSelected?.invoke(TaskFilters.BYDATE)
            dismiss()
        }

        binding.tvSortTitle.setOnClickListener {
            filterSelected?.invoke(TaskFilters.BYTITLE)
            dismiss()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FilterBottomSheet()
    }
}