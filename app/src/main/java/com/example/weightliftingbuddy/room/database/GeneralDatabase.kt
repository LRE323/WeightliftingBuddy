package com.example.weightliftingbuddy.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.ExerciseSet
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import com.example.weightliftingbuddy.room.dao.ExerciseSetDao

@Database(
    entities = [Exercise::class, ExerciseSet::class],
    version = 1
)
abstract class GeneralDatabase: RoomDatabase() {

    companion object {
        const val NAME = "general.database"
    }

    abstract val exerciseDao: ExerciseDao
    abstract val exerciseSetDao: ExerciseSetDao
}