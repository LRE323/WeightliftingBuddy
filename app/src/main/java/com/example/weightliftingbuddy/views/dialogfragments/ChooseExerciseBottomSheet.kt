package com.example.weightliftingbuddy.views.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.views.adapters.ExerciseListAdapter
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.databinding.BottomSheetChooseExerciseBinding
import com.example.weightliftingbuddy.data.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/*
TODO: Delete this class
 */
class ChooseExerciseBottomSheet(val exerciseList: List<Exercise>): BottomSheetDialogFragment() {
    private var binding: BottomSheetChooseExerciseBinding? = null

    // RecyclerView stuff
    private var recyclerViewExerciseList: RecyclerView? = null
    private var adapterExerciseList: ExerciseListAdapter? = null

    companion object {
        const val TAG = "CHOOSE_EXERCISE_BOTTOM_SHEET"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BottomSheetChooseExerciseBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_choose_exercise, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapterExerciseList = ExerciseListAdapter(exerciseList, null)
        recyclerViewExerciseList = binding?.chooseExerciseBottomSheetList
        recyclerViewExerciseList?.apply {
            adapter = adapterExerciseList
            layoutManager = LinearLayoutManager(context)
        }
    }
}