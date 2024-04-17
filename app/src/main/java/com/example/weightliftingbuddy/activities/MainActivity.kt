package com.example.weightliftingbuddy.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.viewmodels.MainActivityViewModel
import com.example.weightliftingbuddy.databinding.LayoutHomePageBinding
import com.example.weightliftingbuddy.models.Workout

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
    }

    override fun onStart() {
        super.onStart()
        setOnClickListeners()
        Workout.getDummyWorkout().printInfo("Luis")
    }

    private fun setOnClickListeners() {
        binding?.apply {
            logWorkoutScreenButton.setOnClickListener(logWorkoutOnClick)
        }
    }

    private val logWorkoutOnClick: OnClickListener = OnClickListener {
        val intent = Intent(this, LogExerciseActivity::class.java)
        startActivity(intent)
    }
}