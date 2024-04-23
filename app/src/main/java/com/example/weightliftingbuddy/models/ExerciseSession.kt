package com.example.weightliftingbuddy.models

import android.util.Log

class ExerciseSession(
    var exercise: Exercise? = null,
    var listOfExerciseSets: ArrayList<ExerciseSet>? = null) {

    companion object {

        fun getDummyExercise(amountOfSets: Int): ExerciseSession {
            val exerciseSets: ArrayList<ExerciseSet> = arrayListOf()
            for (i in 1..amountOfSets) {
                val newLift = ExerciseSet.buildDummyLiftSet(i)
                exerciseSets.add(newLift)
            }
            return ExerciseSession(Exercise("Squat"), exerciseSets)
        }
    }

    fun printInfo(logTag: String = "Luis") {
        Log.i(logTag, "--- ${exercise?.exerciseName?.uppercase()} ---")
        listOfExerciseSets?.forEach {
            it.printInfo(logTag)
        }
    }
}