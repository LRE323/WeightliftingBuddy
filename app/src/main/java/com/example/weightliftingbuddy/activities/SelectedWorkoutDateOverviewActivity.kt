package com.example.weightliftingbuddy.activities

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.viewmodels.SelectedWorkoutDateOverviewViewModel
import java.util.Calendar

class SelectedWorkoutDateOverviewActivity : AppCompatActivity(), OnDateSetListener {
    private var viewModel: SelectedWorkoutDateOverviewViewModel? = null
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SelectedWorkoutDateOverviewViewModel::class.java]
        binding = LayoutSelectedWorkoutOverviewBinding.inflate(layoutInflater)
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
            liveDataSelectedDate.observe(this@SelectedWorkoutDateOverviewActivity, onDateSelected)
        }
    }

    private val onDateSelected = Observer<Calendar> {
        binding?.apply {
            workoutDate.text = GeneralUtilities.getFormattedWorkoutDate(it.time)
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            workoutDate.setOnClickListener(onClickWorkoutDate)
            noWorkoutLoggedView.buttonAddNewActivity.setOnClickListener {
                startActivity(Intent(this@SelectedWorkoutDateOverviewActivity, HomeActivity::class.java))
            }
        }
    }

    private val onClickWorkoutDate: OnClickListener = OnClickListener {
        val selectedDate = viewModel?.liveDataSelectedDate?.value
        selectedDate?.apply {
            showWorkoutDatePickerDialog(this)
        }
    }

    private fun showWorkoutDatePickerDialog(selectedDate: Calendar) {
        val year = selectedDate.get(Calendar.YEAR)
        val monthOfYear = selectedDate.get(Calendar.MONTH)
        val dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this@SelectedWorkoutDateOverviewActivity, 0, this@SelectedWorkoutDateOverviewActivity, year, monthOfYear, dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.Builder().build()
        calendar.set(year, month, dayOfMonth)
        viewModel?.liveDataSelectedDate?.value = calendar
    }
}