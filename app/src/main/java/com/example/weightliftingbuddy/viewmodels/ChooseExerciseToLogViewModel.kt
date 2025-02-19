package com.example.weightliftingbuddy.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.repositories.ExerciseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseExerciseToLogViewModel @Inject constructor(private val exerciseRepository: ExerciseRepository) : ViewModel() {
    private var _exerciseList: MutableLiveData<Event<List<Exercise>>> = MutableLiveData()
    val exerciseList: LiveData<Event<List<Exercise>>> get() = _exerciseList

    init {
        fetchExercises()
    }

    private fun fetchExercises() {
        CoroutineScope(Dispatchers.IO).launch {
            val exerciseList = exerciseRepository.fetchCreatedExercisesAlphabeticallyOrdered()
            _exerciseList.postValue(Event(exerciseList))
        }
    }
}