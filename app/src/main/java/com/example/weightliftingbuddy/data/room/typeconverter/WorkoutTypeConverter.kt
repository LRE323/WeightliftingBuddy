package com.example.weightliftingbuddy.data.room.typeconverter

import androidx.room.TypeConverter
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.google.gson.reflect.TypeToken
import java.util.Date

class WorkoutTypeConverter: BaseTypeConverter() {

    // Convert Date to Long (timestamp) for storage
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    // Convert Long (timestamp) back to Date
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    // Convert list of ExerciseSession to JSON string
    @TypeConverter
    fun fromExerciseSessionList(list: ArrayList<ExerciseSession>?): String {
        return gson.toJson(list)
    }

    // Convert JSON string back to list of ExerciseSession
    @TypeConverter
    fun toExerciseSessionList(data: String?): ArrayList<ExerciseSession>? {
        if (data.isNullOrEmpty()) return arrayListOf()
        val listType = object : TypeToken<ArrayList<ExerciseSession>>() {}.type
        return gson.fromJson(data, listType)
    }
}