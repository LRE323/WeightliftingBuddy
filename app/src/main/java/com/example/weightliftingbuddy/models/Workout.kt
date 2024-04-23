package com.example.weightliftingbuddy.models

import android.util.Log
import com.example.weightliftingbuddy.GeneralUtilities
import java.util.Date

class Workout(
    val workoutDate: Date,
    val listOfExerciseSessions: ArrayList<ExerciseSession>
) {

    companion object {

        fun getDummyWorkout(): Workout {
            val listOfExerciseSessions = arrayListOf<ExerciseSession>()
            listOfExerciseSessions.add(ExerciseSession.getDummyExercise(4))
            listOfExerciseSessions.add(ExerciseSession.getDummyExercise(6))
            return Workout(Date(), listOfExerciseSessions)

        }
    }

    fun printInfo(logTag: String = "Luis") {
        Log.i(logTag, "------ ${GeneralUtilities.getFormattedWorkoutDate(workoutDate)} ------")
        listOfExerciseSessions.forEach {
            it.printInfo(logTag)
        }
    }
}