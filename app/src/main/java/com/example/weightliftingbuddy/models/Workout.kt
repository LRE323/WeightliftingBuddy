package com.example.weightliftingbuddy.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Workout(
    val workoutDate: Date,
    val listOfExerciseSessions: ArrayList<ExerciseSession>
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}