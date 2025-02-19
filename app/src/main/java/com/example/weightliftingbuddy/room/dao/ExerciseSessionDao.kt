package com.example.weightliftingbuddy.room.dao

import androidx.room.Dao
import androidx.room.Insert
import com.example.weightliftingbuddy.data.models.ExerciseSession

@Dao
interface ExerciseSessionDao {

    @Insert()
    suspend fun insertExerciseSession(exerciseSession: ExerciseSession)
}