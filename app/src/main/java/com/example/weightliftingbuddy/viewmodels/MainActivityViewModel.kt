package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class MainActivityViewModel: ViewModel() {
    val liveDataSelectedDate: MutableLiveData<Date> = MutableLiveData()

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        liveDataSelectedDate.apply {
            if (this.value == null) {
                value = Date()
            }
        }
    }
}