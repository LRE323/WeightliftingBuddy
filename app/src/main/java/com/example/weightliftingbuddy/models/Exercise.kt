package com.example.weightliftingbuddy.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey
    var exerciseName: String) {
}