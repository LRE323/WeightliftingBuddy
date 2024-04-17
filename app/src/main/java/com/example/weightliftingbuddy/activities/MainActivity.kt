package com.example.weightliftingbuddy.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.databinding.LayoutHomePageBinding
import com.example.weightliftingbuddy.viewmodels.MainActivityViewModel
import java.util.Date

class MainActivity : ComponentActivity() {
    private var viewModel: MainActivityViewModel? = null
    private var binding: LayoutHomePageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = LayoutHomePageBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
        }
        initObservers()
    }

    override fun onStart() {
        super.onStart()
        setOnClickListeners()
    }

    private fun initObservers() {
        viewModel?.apply {
            liveDataSelectedDate.observe(this@MainActivity, onWorkoutDateSet)
        }
    }

    private val onWorkoutDateSet = Observer<Date> {
        binding?.apply {
            workoutDate.text = GeneralUtilities.getFormattedWorkoutDate(it)
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            workoutDate.setOnClickListener {
                // Todo...
            }
        }
    }
}