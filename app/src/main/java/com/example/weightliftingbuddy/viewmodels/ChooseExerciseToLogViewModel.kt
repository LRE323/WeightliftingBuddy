package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise

class ChooseExerciseToLogViewModel : ViewModel() {
    private var _exerciseList: MutableLiveData<Event<ArrayList<Exercise>>> = MutableLiveData()
    val exerciseList: LiveData<Event<ArrayList<Exercise>>> get() = _exerciseList

    fun init(exerciseListFromExtras: ArrayList<Exercise>?) {
        exerciseListFromExtras?.apply {
            _exerciseList.value = Event(this)
        }
    }
}