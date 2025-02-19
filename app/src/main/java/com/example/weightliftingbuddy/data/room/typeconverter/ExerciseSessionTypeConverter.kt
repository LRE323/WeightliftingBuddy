package com.example.weightliftingbuddy.data.room.typeconverter

import androidx.room.TypeConverter
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.models.ExerciseSet
import com.google.gson.reflect.TypeToken

class ExerciseSessionTypeConverter: BaseTypeConverter() {
    @TypeConverter
    fun fromExercise(exercise: Exercise?): String? {
        return exercise?.exerciseName
    }

    @TypeConverter
    fun toExercise(exerciseName: String?): Exercise? {
        return exerciseName?.let { Exercise(it) }
    }

    @TypeConverter
    fun fromExerciseSetList(list: ArrayList<ExerciseSet>?): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toExerciseSetList(data: String?): ArrayList<ExerciseSet>? {
        if (data.isNullOrEmpty()) return arrayListOf()
        val listType = object : TypeToken<ArrayList<ExerciseSet>>() {}.type
        return gson.fromJson(data, listType)
    }

}