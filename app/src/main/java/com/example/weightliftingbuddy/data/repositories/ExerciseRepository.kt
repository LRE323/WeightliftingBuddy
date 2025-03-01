package com.example.weightliftingbuddy.data.repositories

import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.room.dao.ExerciseDao
import javax.inject.Inject

class ExerciseRepository @Inject constructor(private val exerciseDao: ExerciseDao) {

    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }

    suspend fun fetchCreatedExercisesAlphabeticallyOrdered(): List<Exercise> {
        return exerciseDao.fetchCreatedExercisesAlphabeticallyOrdered()
    }

    suspend fun deleteExercise(exercise: Exercise) {
        exerciseDao.deleteExercise(exercise)
    }
}