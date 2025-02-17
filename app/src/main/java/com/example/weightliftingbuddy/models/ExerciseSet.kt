package com.example.weightliftingbuddy.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExerciseSet(
    var repetitions: Int = 0,
    var weight: Double = 0.0
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}