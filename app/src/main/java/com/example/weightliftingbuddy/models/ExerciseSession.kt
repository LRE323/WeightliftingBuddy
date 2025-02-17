package com.example.weightliftingbuddy.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseSession(
    var exercise: Exercise? = null,
    var listOfExerciseSets: ArrayList<ExerciseSet>? = null) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}