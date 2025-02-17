package com.example.weightliftingbuddy.repositories

import com.example.weightliftingbuddy.models.Workout
import com.example.weightliftingbuddy.room.dao.WorkoutDao
import javax.inject.Inject

class WorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao) {

    suspend fun fetchWorkouts(): List<Workout> {
        return workoutDao.fetchWorkouts()
    }

    suspend fun insertWorkout(workout: Workout) {
        workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        workoutDao.updateWorkout(workout)
    }

}