package com.example.weightliftingbuddy.repositories

import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.dao.ExerciseDao

class ExerciseRepository(private val exerciseDao: ExerciseDao) {

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