package com.example.weightliftingbuddy.room.typeconverter

import androidx.room.TypeConverter
import com.example.weightliftingbuddy.models.Exercise

class ExerciseSessionTypeConverters {

    @TypeConverter
    fun exerciseToArray(exercise: Exercise): Array<String> {
        return arrayOf(
            exercise.exerciseName
        )
    }

    @TypeConverter
    fun arrayToExercise(array: Array<String>): Exercise {
        try {
            val exerciseName = array[0]
            return Exercise(exerciseName)
        } catch (exception: Exception) {
            throw java.lang.Exception("TypeConverterFailure: Failed to convert Array to Exercise")
        }
    }
}