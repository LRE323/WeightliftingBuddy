package com.example.weightliftingbuddy.views.activities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.data.models.ExerciseSession

class LogExerciseSessionViewModel : ViewModel() {
    private val _exerciseSession: MutableLiveData<ExerciseSession?> = MutableLiveData(null)
    val exerciseSession: LiveData<ExerciseSession?> get() = this._exerciseSession

    fun initData(exerciseSession: ExerciseSession?) {
        _exerciseSession.value = exerciseSession
    }
}