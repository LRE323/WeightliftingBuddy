package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    fun getToastMessage(): String {
        return "ViewModel test"
    }

}