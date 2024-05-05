package com.example.weightliftingbuddy.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weightliftingbuddy.models.Exercise

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExercise(exercise: Exercise)

    @Query("SELECT * FROM exercise ORDER BY exerciseName COLLATE NOCASE ASC")
    suspend fun fetchCreatedExercises(): List<Exercise>

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

}