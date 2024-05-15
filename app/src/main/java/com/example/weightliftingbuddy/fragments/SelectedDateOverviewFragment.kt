package com.example.weightliftingbuddy.fragments

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.activities.ChooseExerciseToLogActivity
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.database.ExerciseDatabase
import com.example.weightliftingbuddy.viewmodels.SelectedWorkoutDateOverviewViewModel
import java.util.ArrayList
import java.util.Calendar

class SelectedDateOverviewFragment : Fragment(), OnDateSetListener {
    
    // ViewModel stuff
    private var exerciseDatabase: ExerciseDatabase? = null
    private var lazyViewModel: Lazy<SelectedWorkoutDateOverviewViewModel>? = null
    private var viewModel: SelectedWorkoutDateOverviewViewModel? = null

    // Related to Views
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.apply {
            exerciseDatabase = Room.databaseBuilder(applicationContext, ExerciseDatabase::class.java, ExerciseDatabase.NAME).build()
        }
        initViewModel()
        binding = LayoutSelectedWorkoutOverviewBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding?.root
    }

    private fun initViewModel() {
        lazyViewModel = activity?.viewModels<SelectedWorkoutDateOverviewViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return exerciseDatabase?.dao?.let { SelectedWorkoutDateOverviewViewModel(it) } as T
                }
            }
        })
        viewModel = lazyViewModel?.value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setOnClickListeners()
    }

    private fun initObservers() {
        viewModel?.apply {

            liveDataSelectedDate.observe(viewLifecycleOwner, onDateSelected)

            createdExercises.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.apply {
                    val intent = ChooseExerciseToLogActivity.getIntent(requireContext(), ArrayList(this))
                    startActivity(intent)
                }
            }
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

            workoutDateNext.setOnClickListener {
                viewModel?.incrementSelectedWorkoutDate(1)
            }

            workoutDatePrevious.setOnClickListener {
                viewModel?.incrementSelectedWorkoutDate(-1)
            }

            fabLogWorkout.setOnClickListener { viewModel?.fetchCreatedExercises() }
        }
    }

    private val onClickWorkoutDate: View.OnClickListener = View.OnClickListener {
        val selectedDate = viewModel?.liveDataSelectedDate?.value
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
        val calendar = Calendar.Builder().build()
        calendar.set(year, month, dayOfMonth)
        viewModel?.liveDataSelectedDate?.value = calendar
    }
}