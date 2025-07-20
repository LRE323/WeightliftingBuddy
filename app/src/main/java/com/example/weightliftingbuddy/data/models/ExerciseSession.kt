package com.example.weightliftingbuddy.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Entity @Parcelize
data class ExerciseSession(
    var exercise: Exercise? = null,
    var listOfExerciseSets: ArrayList<ExerciseSet>? = null): Parcelable {

    @IgnoredOnParcel
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}