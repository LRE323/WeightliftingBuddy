package com.example.weightliftingbuddy.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.ExerciseSet
import com.example.weightliftingbuddy.room.dao.ExerciseDao
import com.example.weightliftingbuddy.room.dao.ExerciseSetDao
import com.example.weightliftingbuddy.room.typeconverter.ExerciseSessionTypeConverters
import com.example.weightliftingbuddy.room.typeconverter.WorkoutTypeConverter

@Database(
    entities = [Exercise::class, ExerciseSet::class],
    version = 1
)
@TypeConverters(ExerciseSessionTypeConverters::class, WorkoutTypeConverter::class)
abstract class GeneralDatabase: RoomDatabase() {

    companion object {
        const val NAME = "general.database"
    }

    abstract val exerciseDao: ExerciseDao
    abstract val exerciseSetDao: ExerciseSetDao
}