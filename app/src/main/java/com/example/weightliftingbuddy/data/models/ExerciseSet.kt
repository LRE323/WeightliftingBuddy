package com.example.weightliftingbuddy.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
data class ExerciseSet(
    var repetitions: Int = 0,
    var weight: Double = 0.0
): Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}