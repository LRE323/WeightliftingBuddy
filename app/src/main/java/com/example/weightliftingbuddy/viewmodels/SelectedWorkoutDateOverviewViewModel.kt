package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.Workout
import com.example.weightliftingbuddy.repositories.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
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
    val workoutForSelectedDate: MutableLiveData<Workout> = MutableLiveData()

    /**
     * List of all recorded workouts.
     */
    val workoutList: MutableLiveData<List<Workout>> = MutableLiveData()

    private val _createdExercises: MutableLiveData<Event<List<Exercise>>> = MutableLiveData()
    val createdExercises: LiveData<Event<List<Exercise>>> get() = _createdExercises

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        selectedDate.apply {
            if (this.value == null) {
                value = Calendar.getInstance()
            }
        }
    }


    fun incrementSelectedWorkoutDate(by: Int) {
        val workoutDateToSet = selectedDate.value
        workoutDateToSet?.apply {
            add(Calendar.DAY_OF_MONTH, by)
            selectedDate.postValue(this)
        }
    }

    fun fetchWorkouts() {
        CoroutineScope(Dispatchers.IO).launch {
            workoutList.postValue(workoutRepository.fetchWorkouts())
        }
    }

    fun insertWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.insertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    fun updateWorkout(workout: Workout) {
        CoroutineScope(Dispatchers.IO).launch {
            workoutRepository.updateWorkout(workout)
        }
    }

}