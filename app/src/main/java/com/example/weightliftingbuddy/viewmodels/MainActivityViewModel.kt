package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Date

class MainActivityViewModel: ViewModel() {
    val liveDataSelectedDate: MutableLiveData<Date> = MutableLiveData()

    init {
        liveDataSelectedDate.apply {
            if (this.value == null) {
                value = Date()
            }
        }
    }
}