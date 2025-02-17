package com.example.weightliftingbuddy.models

import android.util.Log
import com.example.weightliftingbuddy.GeneralUtilities
import java.util.Date

data class Workout(
    val workoutDate: Date,
    val listOfExerciseSessions: ArrayList<ExerciseSession>
) {
}