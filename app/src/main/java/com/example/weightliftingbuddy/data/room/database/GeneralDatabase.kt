package com.example.weightliftingbuddy.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.example.weightliftingbuddy.data.models.ExerciseSet
import com.example.weightliftingbuddy.data.models.Workout
import com.example.weightliftingbuddy.data.room.dao.ExerciseDao
import com.example.weightliftingbuddy.data.room.dao.ExerciseSessionDao
import com.example.weightliftingbuddy.data.room.dao.ExerciseSetDao
import com.example.weightliftingbuddy.data.room.dao.WorkoutDao
import com.example.weightliftingbuddy.data.room.typeconverter.ExerciseSessionTypeConverter
import com.example.weightliftingbuddy.data.room.typeconverter.WorkoutTypeConverter

@Database(
    entities = [Exercise::class, ExerciseSet::class, ExerciseSession::class, Workout::class],
    version = 1
)
@TypeConverters(ExerciseSessionTypeConverter::class, WorkoutTypeConverter::class)
abstract class GeneralDatabase: RoomDatabase() {

    companion object {
        const val NAME = "general.database"
    }

    abstract val exerciseDao: ExerciseDao
    abstract val exerciseSetDao: ExerciseSetDao
    abstract val exerciseSessionDao: ExerciseSessionDao
    abstract val workoutDao: WorkoutDao
}