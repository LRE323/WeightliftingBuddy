package com.example.weightliftingbuddy.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.dao.ExerciseDao

@Database(
    entities = [Exercise::class],
    version = 1
)
abstract class ExerciseDatabase: RoomDatabase() {

    companion object {
        const val NAME = "exercise.database"
    }

    abstract val exerciseDao: ExerciseDao
}