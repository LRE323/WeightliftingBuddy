package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor (private val exerciseDao: ExerciseDao): ViewModel() {
    val exerciseList: MutableLiveData<List<Exercise>> = MutableLiveData()

    private val _onDeleteExerciseSuccess: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val onDeleteExerciseSuccess: LiveData<Event<Boolean>> get() = _onDeleteExerciseSuccess

    var exerciseToDelete: Exercise? = null

    private val _onCreateNewExerciseSuccess: MutableLiveData<Event<Exercise>> = MutableLiveData()
    val onCreateNewExerciseSuccess: LiveData<Event<Exercise>> get() = _onCreateNewExerciseSuccess

    fun saveExercise(exercise: Exercise) {
        CoroutineScope(Dispatchers.IO).launch {
            exerciseDao.apply {
                insertExercise(exercise)
                // TODO: Should not post value if nothing is inserted.
                exerciseList.postValue(fetchCreatedExercisesAlphabeticallyOrdered())
                _onCreateNewExerciseSuccess.postValue(Event(exercise))
            }
        }
    }

    fun deleteExercise(exercise: Exercise?) {
        if (exercise != null) {
            CoroutineScope(Dispatchers.IO).launch {
                exerciseDao.deleteExercise(exercise)
                _onDeleteExerciseSuccess.postValue(Event(true))
            }
        }
    }

    fun fetchExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            val fetchedExerciseList = exerciseDao.fetchCreatedExercisesAlphabeticallyOrdered()
            exerciseList.postValue(fetchedExerciseList)
        }
    }
}