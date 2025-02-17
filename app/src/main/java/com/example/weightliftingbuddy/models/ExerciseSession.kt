package com.example.weightliftingbuddy.models

data class ExerciseSession(
    var exercise: Exercise? = null,
    var listOfExerciseSets: ArrayList<ExerciseSet>? = null)