package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.Workout
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class SelectedWorkoutDateOverviewViewModel(private val exerciseDao: ExerciseDao): ViewModel() {
    /**
     * The current date that has been set or selected, either by default or by the user
     */
    val liveDataSelectedDate: MutableLiveData<Calendar> = MutableLiveData()

    /**
     * The workout that was done on the selected date (liveDataSelectedDate).
     */
    val liveDataWorkoutForSelectedDate: MutableLiveData<Workout> = MutableLiveData()

    val liveDataListOfWorkouts: MutableLiveData<ArrayList<Workout>> = MutableLiveData()

    private val _createdExercises: MutableLiveData<Event<List<Exercise>>> = MutableLiveData()
    val createdExercises: LiveData<Event<List<Exercise>>> get() = _createdExercises

    init {
        // Set the value of liveDataSelectedDate to today's date by default.
        liveDataSelectedDate.apply {
            if (this.value == null) {
                value = Calendar.getInstance()
            }
        }
    }


    fun incrementSelectedWorkoutDate(by: Int) {
        val workoutDateToSet = liveDataSelectedDate.value
        workoutDateToSet?.apply {
            add(Calendar.DAY_OF_MONTH, by)
            liveDataSelectedDate.postValue(this)
        }
    }

    fun fetchCreatedExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            val createdExercises = exerciseDao.fetchCreatedExercisesAlphabeticallyOrdered()
            _createdExercises.postValue(Event(createdExercises))
        }
    }
}