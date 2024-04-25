package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseListViewModel(private val exerciseDao: ExerciseDao): ViewModel() {
    val exerciseList: MutableLiveData<List<Exercise>> = MutableLiveData()

    fun saveExercise(exercise: Exercise) {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseDao.apply {
                insertExercise(exercise)
                // TODO: Should not post value if nothing is inserted.
                exerciseList.postValue(getExercises())
            }
        }
    }

    fun fetchExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseList.postValue(exerciseDao.getExercises())
        }
    }
}