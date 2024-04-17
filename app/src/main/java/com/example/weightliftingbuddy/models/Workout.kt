package com.example.weightliftingbuddy.models

import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date

class Workout(
    val workoutDate: Date,
    val listOfExercises: ArrayList<Exercise>
) {

    companion object {
        private const val DEFAULT_WORKOUT_DATE_FORMAT = "EEE MMM dd"

        fun getDummyWorkout(): Workout {
            val listOfExercises = arrayListOf<Exercise>()
            listOfExercises.add(Exercise.getDummyExercise(4))
            listOfExercises.add(Exercise.getDummyExercise(6))
            return Workout(Date(), listOfExercises)

        }
    }

    fun printInfo(logTag: String = "Luis") {
        Log.i(logTag, "------ ${getFormattedWorkoutDate()} ------")
        listOfExercises.forEach {
            it.printInfo(logTag)
        }
    }

    fun getFormattedWorkoutDate(): String {
        val simpleDateFormat = SimpleDateFormat(DEFAULT_WORKOUT_DATE_FORMAT)
        return simpleDateFormat.format(workoutDate)
    }
}