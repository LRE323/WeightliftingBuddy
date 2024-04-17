package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Workout

class MainActivityViewModel: ViewModel() {
    val liveDataWorkoutSelected: MutableLiveData<Workout> by lazy {
        MutableLiveData<Workout>()
    }
}