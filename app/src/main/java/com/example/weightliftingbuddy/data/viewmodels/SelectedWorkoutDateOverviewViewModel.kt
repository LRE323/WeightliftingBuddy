package com.example.weightliftingbuddy.data.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    var exerciseSessionQueuedForDeletion: ExerciseSession? = null

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
        _workoutForSelectedDate.value = workoutRepository.findWorkout(_workoutList.value, calendar.time)

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
            val workout = workoutRepository.findWorkout(_workoutList.value, this.time)
            _workoutForSelectedDate.postValue(workout)
        }
    }

    private fun fetchWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            val workoutList = workoutRepository.fetchWorkouts()
            _workoutList.postValue(workoutList)

            val selectedDate = selectedDate.value
            if (selectedDate != null) {
                val workout = workoutRepository.findWorkout(workoutList, selectedDate.time)
                _workoutForSelectedDate.postValue(workout)
            }
        }
    }

    private fun insertWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.requestWorkoutInsertion(workout)

            // refresh the workouts list after insertion
            fetchWorkouts()
        }
    }

    fun deleteWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    fun deleteExerciseSessionFromWorkout(workout: Workout) {
        val exerciseSessionList = workout.listOfExerciseSessions
        var indexToRemove: Int? = null

        // Check if the ExerciseSession exists in the Workout.
        if (exerciseSessionList.contains(exerciseSessionQueuedForDeletion)) {

            exerciseSessionList.forEach {
                if (it.id == exerciseSessionQueuedForDeletion?.id) {
                    indexToRemove = exerciseSessionList.indexOf(it)
                }
            }
            indexToRemove?.apply {
                // Remove the ExerciseSession from the Workout.
                exerciseSessionList.remove(exerciseSessionList[this])
                // Update the Workout in Room.
                updateWorkout(workout)
            }
        }
        exerciseSessionQueuedForDeletion = null
    }

    private fun updateWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.updateWorkout(workout)

            // refresh the workouts list after update
            fetchWorkouts()
        }
    }

    /**
     * Runs ViewModel logic after the user selects a Exercise to log.
     */
    fun onReceiveExerciseSelected(exerciseSelected: Exercise) {
        // Do nothing if selectedDate is null.
        val selectedDate = selectedDate.value?.time ?: return

        if (workoutRepository.dateHasWorkout(selectedDate)) {
            // Create a new ExerciseSession for the selected Exercise.
            val newExerciseSession = ExerciseSession(exerciseSelected)
            // Get the Workout for the currently selected Date.
            val workoutForSelectedDate = workoutForSelectedDate.value
            if (workoutForSelectedDate != null) {
                // Add the new ExerciseSession to the Workout for the selected Date.
                workoutForSelectedDate.listOfExerciseSessions.add(newExerciseSession)
                //  Update the Workout in Room.
                updateWorkout(workoutForSelectedDate)
            }
        } else {
            // Create and add a new Workout.
            val newWorkout = getNewWorkoutFromExerciseSelected(exerciseSelected, selectedDate)
            insertWorkout(newWorkout)
        }
    }

    fun getWorkoutForSelectedDate(): Workout? {
        return workoutForSelectedDate.value
    }
}