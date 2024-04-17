package com.example.weightliftingbuddy

import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {

    fun getToastMessage(): String {
        return "ViewModel test"
    }

}