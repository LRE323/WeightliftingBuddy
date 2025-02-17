package com.example.weightliftingbuddy.models

import java.util.Date

data class Workout(
    val workoutDate: Date,
    val listOfExerciseSessions: ArrayList<ExerciseSession>
)