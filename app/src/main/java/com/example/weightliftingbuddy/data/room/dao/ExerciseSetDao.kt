package com.example.weightliftingbuddy.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.weightliftingbuddy.data.models.ExerciseSet

@Dao
interface ExerciseSetDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertExerciseSet(exerciseSet: ExerciseSet)

    @Delete
    suspend fun deleteExerciseSet(exerciseSet: ExerciseSet)
}