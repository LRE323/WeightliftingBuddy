package com.example.weightliftingbuddy.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.activities.ChooseExerciseToLogActivity
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.Workout
import com.example.weightliftingbuddy.viewmodels.SelectedWorkoutDateOverviewViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class SelectedDateOverviewFragment : BaseFragment(), OnDateSetListener {
    private val viewModel: SelectedWorkoutDateOverviewViewModel by viewModels()
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null

    private val chooseExerciseToLogResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.apply {
                val exerciseSelected = this.getParcelableExtra<Exercise>(SELECTED_EXERCISE)
                if (exerciseSelected != null) {
                    viewModel.onReceiveExerciseSelected(exerciseSelected)
                }
            }
        }
    }

    companion object {
        const val SELECTED_EXERCISE = "SELECTED_EXERCISE"
    }

    override fun initBinding() {
        binding = LayoutSelectedWorkoutOverviewBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        // Nothing to do here
    }

    override fun setOnClickListeners() {
        getWorkoutDateTextView()?.setOnClickListener(onClickWorkoutDate)

        getWorkoutDateNextIcon()?.setOnClickListener {
            viewModel.incrementSelectedWorkoutDate(1)
        }

        getWorkoutDatePreviousIcon()?.setOnClickListener {
            viewModel.incrementSelectedWorkoutDate(-1)
        }

        getLogWorkoutFab()?.setOnClickListener {
            val intent = Intent(requireContext(), ChooseExerciseToLogActivity::class.java)
            chooseExerciseToLogResult.launch(intent)
        }
    }

    override fun initObservers() {
        viewModel.apply {
            selectedDate.observe(viewLifecycleOwner, onDateSelected)
            workoutForSelectedDate.observe(viewLifecycleOwner, onWorkoutForSelectedDateReceived)
        }
    }

    private val onDateSelected = Observer<Calendar> {
        binding?.apply {
            getWorkoutDateTextView()?.text = GeneralUtilities.getFormattedWorkoutDate(it.time)
        }
    }

    private val onClickWorkoutDate: View.OnClickListener = View.OnClickListener {
        val selectedDate = viewModel.selectedDate.value
        selectedDate?.apply {
            showWorkoutDatePickerDialog(this)
        }
    }

    private fun showWorkoutDatePickerDialog(selectedDate: Calendar) {
        var datePickerDialog: DatePickerDialog?
        selectedDate.apply {
            val year = get(Calendar.YEAR)
            val monthOfYear = get(Calendar.MONTH)
            val dayOfMonth = get(Calendar.DAY_OF_MONTH)
            datePickerDialog = DatePickerDialog(requireContext(), 0, this@SelectedDateOverviewFragment, year, monthOfYear, dayOfMonth)
        }
        datePickerDialog?.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.onWorkoutDateSet(year, month, dayOfMonth)
    }

    override fun setBindingNull() {
        binding = null
    }

    override fun getBinding(): ViewDataBinding? {
        return binding
    }

    private fun getWorkoutDateTextView(): TextView? {
        return binding?.layoutWorkoutDateArea?.workoutDate
    }

    private fun getWorkoutDateNextIcon(): ImageView? {
        return binding?.layoutWorkoutDateArea?.workoutDateNext
    }

    private fun getWorkoutDatePreviousIcon(): ImageView? {
        return binding?.layoutWorkoutDateArea?.workoutDatePrevious
    }

    private fun getLogWorkoutFab(): FloatingActionButton? {
        return binding?.fabLogWorkout
    }

    private val onWorkoutForSelectedDateReceived = Observer<Workout?> { workout ->
    }
}