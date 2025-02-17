package com.example.weightliftingbuddy.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weightliftingbuddy.models.Workout

@Dao
interface WorkoutDao {

    @Query("SELECT * FROM workout ORDER BY workoutDate")
    suspend fun getAllWorkouts(): List<Workout>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Update
    suspend fun updateWorkout(workout: Workout)

}