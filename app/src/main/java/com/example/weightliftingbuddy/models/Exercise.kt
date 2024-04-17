package com.example.weightliftingbuddy.models

import android.util.Log

class Exercise(
    var lift: Lift? = null,
    var listOfLiftSets: ArrayList<LiftSet>? = null) {

    companion object {

        fun getDummyExercise(amountOfSets: Int): Exercise {
            val liftSets: ArrayList<LiftSet> = arrayListOf()
            for (i in 1..amountOfSets) {
                val newLift = LiftSet.buildDummyLiftSet(i)
                liftSets.add(newLift)
            }
            return Exercise(Lift("Squat"), liftSets)
        }
    }

    fun printInfo(logTag: String = "Luis") {
        Log.i(logTag, "--- ${lift?.liftName?.uppercase()} ---")
        listOfLiftSets?.forEach {
            it.printInfo(logTag)
        }
    }
}