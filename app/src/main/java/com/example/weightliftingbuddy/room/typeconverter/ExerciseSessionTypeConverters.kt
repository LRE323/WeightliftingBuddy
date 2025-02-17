package com.example.weightliftingbuddy.room.typeconverter

import androidx.room.TypeConverter
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.models.ExerciseSet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseSessionTypeConverters: BaseTypeConverter() {
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