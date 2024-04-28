package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Workout
import java.util.Calendar

class SelectedWorkoutDateOverviewViewModel: ViewModel() {
    /**
     * The current date that has been set or selected, either by default or by the user
     */
    val liveDataSelectedDate: MutableLiveData<Calendar> = MutableLiveData()

    /**
     * The workout that was done on the selected date (liveDataSelectedDate).
     */
    val liveDataWorkoutForSelectedDate: MutableLiveData<Workout> = MutableLiveData()

    val liveDataListOfWorkouts: MutableLiveData<ArrayList<Workout>> = MutableLiveData()

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        liveDataSelectedDate.apply {
            if (this.value == null) {
                value = Calendar.getInstance()
            }
        }
    }


    fun incrementSelectedWorkoutDate(by: Int) {
        val workoutDateToSet = liveDataSelectedDate.value
        workoutDateToSet?.apply {
            add(Calendar.DAY_OF_MONTH, by)
            liveDataSelectedDate.postValue(this)
        }
    }
}