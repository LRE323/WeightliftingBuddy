package com.example.weightliftingbuddy.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.activities.HomeActivity
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.viewmodels.SelectedWorkoutDateOverviewViewModel
import java.util.Calendar

class SelectedDateOverviewFragment : Fragment(), OnDateSetListener {
    private var viewModel: SelectedWorkoutDateOverviewViewModel? = null
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[SelectedWorkoutDateOverviewViewModel::class.java]
        binding = LayoutSelectedWorkoutOverviewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setOnClickListeners()
    }

    private fun initObservers() {
        viewModel?.apply {
            liveDataSelectedDate.observe(viewLifecycleOwner, onDateSelected)
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
                Toast.makeText(requireContext(), "Click", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onClickWorkoutDate: View.OnClickListener = View.OnClickListener {
        val selectedDate = viewModel?.liveDataSelectedDate?.value
        selectedDate?.apply {
            showWorkoutDatePickerDialog(this)
        }
    }

    private fun showWorkoutDatePickerDialog(selectedDate: Calendar) {
        val year = selectedDate.get(Calendar.YEAR)
        val monthOfYear = selectedDate.get(Calendar.MONTH)
        val dayOfMonth = selectedDate.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(requireContext(), 0, this, year, monthOfYear, dayOfMonth)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.Builder().build()
        calendar.set(year, month, dayOfMonth)
        viewModel?.liveDataSelectedDate?.value = calendar
    }
}