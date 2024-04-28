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

    private val _onDeleteExerciseSuccess: MutableLiveData<Boolean> = MutableLiveData()
    val onDeleteExerciseSuccess get() = _onDeleteExerciseSuccess

    var exerciseToDelete: Exercise? = null

    private val _onCreateNewExerciseSuccess: MutableLiveData<Exercise> = MutableLiveData()
    val onCreateNewExerciseSuccess get() = _onCreateNewExerciseSuccess

    fun saveExercise(exercise: Exercise) {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseDao.apply {
                insertExercise(exercise)
                // TODO: Should not post value if nothing is inserted.
                exerciseList.postValue(getExercises())
                onCreateNewExerciseSuccess.postValue(exercise)
            }
        }
    }

    fun deleteExercise(exercise: Exercise?) {
        if (exercise != null) {
            CoroutineScope(Dispatchers.IO).launch {
                exerciseDao.deleteExercise(exercise)
                _onDeleteExerciseSuccess.postValue(true)
            }
        }
    }

    fun fetchExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            val fetchedExerciseList = exerciseDao.getExercises()
            exerciseList.postValue(fetchedExerciseList)
        }
    }
}