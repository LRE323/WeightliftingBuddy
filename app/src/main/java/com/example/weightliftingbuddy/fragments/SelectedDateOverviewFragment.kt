package com.example.weightliftingbuddy.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.weightliftingbuddy.GeneralUtilities
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.activities.ChooseExerciseToLogActivity
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.database.ExerciseDatabase
import com.example.weightliftingbuddy.viewmodels.SelectedWorkoutDateOverviewViewModel
import java.util.Calendar

class SelectedDateOverviewFragment : Fragment(), OnDateSetListener {
    
    // ViewModel stuff
    private var exerciseDatabase: ExerciseDatabase? = null
    private var lazyViewModel: Lazy<SelectedWorkoutDateOverviewViewModel>? = null
    private var viewModel: SelectedWorkoutDateOverviewViewModel? = null

    // Binding and Views
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null
    private var tvWorkoutDate: TextView? = null
    private var iconWorkoutDateNext: ImageView? = null
    private var iconWorkoutDatePrevious: ImageView? = null
    private var nsvExercisesLogged: NestedScrollView? = null

    private val chooseExerciseToLogResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.apply {
                val exerciseSelected = this.getParcelableExtra<Exercise>(SELECTED_EXERCISE)
                if (exerciseSelected != null) {
                    onExerciseSelected(exerciseSelected)
                }
            }
        }
    }

    companion object {
        const val SELECTED_EXERCISE = "SELECTED_EXERCISE"
    }

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
                    return exerciseDatabase?.exerciseDao?.let { SelectedWorkoutDateOverviewViewModel(it) } as T
                }
            }
        })
        viewModel = lazyViewModel?.value
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
    }

    private fun initViews() {
        binding?.apply {
            initViewVariables(this)
        }
        setOnClickListeners()
    }

    private fun initViewVariables(binding: LayoutSelectedWorkoutOverviewBinding) {
        binding.apply {
            tvWorkoutDate = this.layoutWorkoutDateArea.workoutDate
            iconWorkoutDateNext = this.layoutWorkoutDateArea.workoutDateNext
            iconWorkoutDatePrevious = this.layoutWorkoutDateArea.workoutDatePrevious
            this@SelectedDateOverviewFragment.nsvExercisesLogged = nsvExercisesLogged
        }
    }

    private fun initObservers() {
        viewModel?.apply {

            liveDataSelectedDate.observe(viewLifecycleOwner, onDateSelected)

            createdExercises.observe(viewLifecycleOwner){
                it.getContentIfNotHandled()?.apply { launchChooseExerciseToLogActivity(ArrayList(this)) }
            }
        }
    }

    private fun launchChooseExerciseToLogActivity(exercisesCreated: ArrayList<Exercise>) {
        val intent = ChooseExerciseToLogActivity.getIntent(requireContext(), exercisesCreated)
        chooseExerciseToLogResult.launch(intent)
    }

    private val onDateSelected = Observer<Calendar> {
        binding?.apply {
            tvWorkoutDate?.text = GeneralUtilities.getFormattedWorkoutDate(it.time)
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            tvWorkoutDate?.setOnClickListener(onClickWorkoutDate)

            iconWorkoutDateNext?.setOnClickListener {
                viewModel?.incrementSelectedWorkoutDate(1)
            }

            iconWorkoutDatePrevious?.setOnClickListener {
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

    /**
     * Prepares and shows the Views displaying Exercise data after the user has selected an Exercise.
     * @param exerciseSelected The exercise that was selected
     */
    private fun onExerciseSelected(exerciseSelected: Exercise) {
        val exerciseView = layoutInflater.inflate(R.layout.layout_exercise_session_logged, null)
        exerciseView.findViewById<TextView>(R.id.tv_exercise_name).text = exerciseSelected.exerciseName
        binding?.apply {
            llSelectedExercises.addView(exerciseView)
            nsvExercisesLogged.visibility = VISIBLE
            homePageNoWorkoutMessage.visibility = GONE
        }
    }
}