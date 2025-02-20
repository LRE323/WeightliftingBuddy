package com.example.weightliftingbuddy.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.utils.GeneralUtilities
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.example.weightliftingbuddy.data.models.Workout
import com.example.weightliftingbuddy.data.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class SelectedWorkoutDateOverviewViewModel @Inject constructor (private val workoutRepository: WorkoutRepository): ViewModel() {
    /**
     * The current date that has been set or selected, either by default or by the user
     */
    val selectedDate: MutableLiveData<Calendar> = MutableLiveData()

    /**
     * The workout that was done on the selected date (liveDataSelectedDate).
     */
    private val _workoutForSelectedDate: MutableLiveData<Workout?> = MutableLiveData()
    val workoutForSelectedDate: LiveData<Workout?> get() = _workoutForSelectedDate

    /**
     * List of all recorded workouts.
     */
    private val _workoutList: MutableLiveData<List<Workout>> = MutableLiveData()
    val workoutList: LiveData<List<Workout>> get() = _workoutList

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        selectedDate.apply {
            if (this.value == null) {
                value = Calendar.getInstance()
            }
        }
        fetchWorkouts()
    }

    fun onWorkoutDateSet(year: Int, month: Int, dayOfMonth: Int) {
        // set the new value for selectedDate
        val calendar = Calendar.Builder().build()
        calendar.set(year, month, dayOfMonth)
        selectedDate.value = calendar

        // find and set the workout for the selected date
        _workoutForSelectedDate.value = findWorkoutForSelectedDate(_workoutList.value, calendar.time)

    }


    private fun findWorkoutForSelectedDate(workoutList: List<Workout>?, selectedDate: Date?): Workout? {
        if (workoutList == null || selectedDate == null) {
            return null
        }
        val formattedSelectedDate = GeneralUtilities.getFormattedWorkoutDate(selectedDate)
        workoutList.forEach {
            val formattedCurrentIterationDate = GeneralUtilities.getFormattedWorkoutDate(it.workoutDate)

            if (formattedSelectedDate == formattedCurrentIterationDate) {
                return it
            }
        }
        return null
    }

    private fun getNewWorkoutFromExerciseSelected(exerciseSelected: Exercise, workoutDate: Date = Calendar.getInstance().time): Workout {
        val exerciseSession = ExerciseSession(exercise = exerciseSelected)
        return Workout(workoutDate, arrayListOf(exerciseSession))
    }

    fun incrementSelectedWorkoutDate(by: Int) {
        val workoutDateToSet = selectedDate.value
        workoutDateToSet?.apply {
            add(Calendar.DAY_OF_MONTH, by)
            selectedDate.postValue(this)
            // Update the Workout for the selected date
            _workoutForSelectedDate.postValue(findWorkoutForSelectedDate(_workoutList.value, this.time))
        }
    }

    private fun fetchWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            val workoutList = workoutRepository.fetchWorkouts()
            _workoutList.postValue(workoutList)

            val selectedDate = selectedDate.value
            if (selectedDate != null) {
                _workoutForSelectedDate.postValue(findWorkoutForSelectedDate(workoutList, selectedDate.time))
            }
        }
    }

    private fun insertWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.insertWorkout(workout)

            // refresh the workouts list after insertion
            fetchWorkouts()
        }
    }

    fun deleteWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    private fun updateWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.updateWorkout(workout)

            // refresh the workouts list after update
            fetchWorkouts()
        }
    }

    fun onReceiveExerciseSelected(exerciseSelected: Exercise) {
        val workoutForSelectedDate = workoutForSelectedDate.value

        if (workoutForSelectedDate == null) {
            // Create a new workout and save with room
            val newWorkout = getNewWorkoutFromExerciseSelected(exerciseSelected)
            insertWorkout(newWorkout)
        } else {
            // Create and add a new ExerciseSession to the current Workout
            val newExerciseSession = ExerciseSession(exerciseSelected)
            workoutForSelectedDate.listOfExerciseSessions.add(newExerciseSession)
            updateWorkout(workoutForSelectedDate)
        }
    }

    fun getWorkoutForSelectedDate(): Workout? {
        return workoutForSelectedDate.value
    }
}