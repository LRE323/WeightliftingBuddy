package com.example.weightliftingbuddy.models

class ExerciseSession(
    var exercise: Exercise? = null,
    var listOfExerciseSets: ArrayList<ExerciseSet>? = null) {

    companion object {

        fun getDummyExercise(amountOfSets: Int): ExerciseSession {
            val exerciseSets: ArrayList<ExerciseSet> = arrayListOf()
            for (i in 1..amountOfSets) {
                val newLift = ExerciseSet.buildDummyLiftSet()
                exerciseSets.add(newLift)
            }
            return ExerciseSession(Exercise("Squat"), exerciseSets)
        }
    }
}