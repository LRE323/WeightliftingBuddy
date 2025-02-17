package com.example.weightliftingbuddy.models

import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.random.Random

@Entity
class ExerciseSet(
    var repetitions: Int = 0,
    var weight: Double = 0.0
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        fun buildDummyLiftSet(): ExerciseSet {
            val randomReps = Random.nextInt(5, 12)
            val randomWeight = Random.nextInt(25, 49) + Random.nextDouble()
            return ExerciseSet(randomReps, randomWeight)
        }
    }

    fun printInfo(logTag: String) {
        val info = "$repetitions rep(s) at $weight ${getWeightUnit()}"
        Log.i(logTag, info)
    }

    private fun getWeightUnit(): String {
        return "lbs"
    }
}