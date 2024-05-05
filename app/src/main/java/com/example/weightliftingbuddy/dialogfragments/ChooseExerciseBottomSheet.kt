package com.example.weightliftingbuddy.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.models.Exercise
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChooseExerciseBottomSheet(val exerciseList: List<Exercise>): BottomSheetDialogFragment() {

    companion object {
        const val TAG = "CHOOSE_EXERCISE_BOTTOM_SHEET"
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_choose_exercise, container)
    }
}