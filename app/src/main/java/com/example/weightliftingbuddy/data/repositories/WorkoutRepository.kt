package com.example.weightliftingbuddy.data.repositories

import android.util.Log
import com.example.weightliftingbuddy.data.models.Workout
import com.example.weightliftingbuddy.data.room.dao.WorkoutDao
import javax.inject.Inject

class WorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao) {

    companion object {
        private const val LOGTAG = "WorkoutRepository"
    }

    suspend fun fetchWorkouts(): List<Workout> {
        return workoutDao.fetchWorkouts()
    }

    suspend fun insertWorkout(workout: Workout) {
        Log.i(LOGTAG, "Inserting the Workout: $workout")
        workoutDao.insertWorkout(workout)
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        Log.i(LOGTAG, "Updating the Workout: $workout")
        workoutDao.updateWorkout(workout)
    }

}