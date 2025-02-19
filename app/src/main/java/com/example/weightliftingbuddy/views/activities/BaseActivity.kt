package com.example.weightliftingbuddy.views.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity

abstract class BaseActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(getLayoutResource())
    }

    abstract fun getLayoutResource(): Int
}