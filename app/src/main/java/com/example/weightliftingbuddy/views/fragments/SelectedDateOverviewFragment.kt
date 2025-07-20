package com.example.weightliftingbuddy.views.fragments

import android.app.Activity
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.example.weightliftingbuddy.data.models.Workout
import com.example.weightliftingbuddy.data.viewmodels.SelectedWorkoutDateOverviewViewModel
import com.example.weightliftingbuddy.databinding.LayoutSelectedWorkoutOverviewBinding
import com.example.weightliftingbuddy.utils.GeneralUtilities
import com.example.weightliftingbuddy.views.activities.ChooseExerciseToLogActivity
import com.example.weightliftingbuddy.views.activities.LogExerciseSessionActivity
import com.example.weightliftingbuddy.views.adapters.ExerciseSessionAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class SelectedDateOverviewFragment : BaseFragment(), OnDateSetListener, ExerciseSessionAdapter.ExerciseSessionAdapterCallback {
    private val viewModel: SelectedWorkoutDateOverviewViewModel by viewModels()
    private var binding: LayoutSelectedWorkoutOverviewBinding? = null
    private var exerciseSessionAdapter: ExerciseSessionAdapter? = null

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
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val workoutForSelectedDate = viewModel.getWorkoutForSelectedDate()
        exerciseSessionAdapter = ExerciseSessionAdapter(workoutForSelectedDate?.listOfExerciseSessions, this)
        binding?.rvExerciseSessions?.apply {
            adapter = exerciseSessionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
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
        exerciseSessionAdapter?.updateList(workout?.listOfExerciseSessions)
        val emptyExerciseSessionList = workout?.listOfExerciseSessions.isNullOrEmpty()
        binding?.apply {
            if (workout == null || emptyExerciseSessionList) {
                rvExerciseSessions.visibility = View.GONE
                homePageNoWorkoutMessage.visibility = View.VISIBLE
            } else {
                rvExerciseSessions.visibility = View.VISIBLE
                homePageNoWorkoutMessage.visibility = View.GONE
            }
        }
    }

    override fun onClickExerciseSession(exerciseSession: ExerciseSession) {
        val bundle = Bundle().apply {
            putParcelable(LogExerciseSessionActivity.EXERCISE_TO_LOG_BUNDLE_KEY, exerciseSession)
        }
        val intent = Intent(context, LogExerciseSessionActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    override fun onLongClickExerciseSession(exerciseSession: ExerciseSession) {
        viewModel.exerciseSessionQueuedForDeletion = exerciseSession
        getConfirmDeleteExerciseSessionDialog(exerciseSession).show()
    }

    private fun getConfirmDeleteExerciseSessionDialog(exerciseSessionToDelete: ExerciseSession): AlertDialog {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
        dialogBuilder.apply {
            val exerciseToDeleteName = exerciseSessionToDelete.exercise?.exerciseName
            setTitle(getString(R.string.exercise_session_delete_confirmation_dialog_title, exerciseToDeleteName))
            setPositiveButton(getString(R.string.delete), onConfirmDeleteExerciseSessionFromWorkout)
            setNegativeButton(getString(R.string.cancel), onCancelDeleteExerciseSessionFromWorkout)
        }
        return dialogBuilder.create()
    }

    private val onConfirmDeleteExerciseSessionFromWorkout = DialogInterface.OnClickListener{ _, _ ->
        val workoutForSelectedDate = viewModel.getWorkoutForSelectedDate()
        if (workoutForSelectedDate != null) {
            viewModel.deleteExerciseSessionFromWorkout(workoutForSelectedDate)
        }
    }

    private val onCancelDeleteExerciseSessionFromWorkout = DialogInterface.OnClickListener{ _, _ ->
        viewModel.exerciseSessionQueuedForDeletion = null
    }
}