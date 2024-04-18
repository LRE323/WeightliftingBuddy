package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Workout
import java.util.Date

class MainActivityViewModel: ViewModel() {
    /**
     * The current date that has been set or selected, either by default or by the user
     */
    val liveDataSelectedDate: MutableLiveData<Date> = MutableLiveData()

    /**
     * The workout that was done on the selected date (liveDataSelectedDate).
     */
    val liveDataWorkoutForSelectedDate: MutableLiveData<Workout> = MutableLiveData()

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        liveDataSelectedDate.apply {
            if (this.value == null) {
                value = Date()
            }
        }
    }
}