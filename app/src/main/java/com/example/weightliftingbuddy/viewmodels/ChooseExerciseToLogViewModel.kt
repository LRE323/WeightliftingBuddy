package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise

class ChooseExerciseToLogViewModel : ViewModel() {
    private var _exerciseList: ArrayList<Exercise>? = null
    val exerciseList: ArrayList<Exercise>? get() = _exerciseList

    fun init(exerciseListFromExtras: ArrayList<Exercise>?) {
        _exerciseList = exerciseListFromExtras
    }
}