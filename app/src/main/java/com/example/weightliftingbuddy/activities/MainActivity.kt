package com.example.weightliftingbuddy.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.databinding.LayoutHomePageBinding
import com.example.weightliftingbuddy.viewmodels.MainActivityViewModel
import java.util.Calendar
import java.util.Date

class MainActivity : ComponentActivity(), OnDateSetListener {
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
            liveDataSelectedDate.observe(this@MainActivity, onDateSelected)
        }
    }

    private val onDateSelected = Observer<Date> {
        binding?.apply {
            workoutDate.text = GeneralUtilities.getFormattedWorkoutDate(it)
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            workoutDate.setOnClickListener(onClickWorkoutDate)
        }
    }

    private val onClickWorkoutDate: OnClickListener = OnClickListener {
        val datePickerDialog = DatePickerDialog(this@MainActivity)
        datePickerDialog.setOnDateSetListener(this)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.Builder().build()
        calendar.set(year, month, dayOfMonth)
        viewModel?.liveDataSelectedDate?.value = calendar.time
    }
}