package com.example.weightliftingbuddy.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.ExerciseSet

@Dao
interface ExerciseSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExerciseSet(exerciseSet: ExerciseSet)

    @Delete
    suspend fun deleteExerciseSet(exerciseSet: ExerciseSet)
}