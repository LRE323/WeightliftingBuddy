package com.example.weightliftingbuddy.models

import android.util.Log
import com.example.weightliftingbuddy.GeneralUtilities
import java.text.SimpleDateFormat
import java.util.Date

class Workout(
    val workoutDate: Date,
    val listOfExercises: ArrayList<Exercise>
) {

    companion object {

        fun getDummyWorkout(): Workout {
            val listOfExercises = arrayListOf<Exercise>()
            listOfExercises.add(Exercise.getDummyExercise(4))
            listOfExercises.add(Exercise.getDummyExercise(6))
            return Workout(Date(), listOfExercises)

        }
    }

    fun printInfo(logTag: String = "Luis") {
        Log.i(logTag, "------ ${GeneralUtilities.getFormattedWorkoutDate(workoutDate)} ------")
        listOfExercises.forEach {
            it.printInfo(logTag)
        }
    }
}