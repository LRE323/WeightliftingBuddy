package com.example.weightliftingbuddy.data.repositories

import android.util.Log
import com.example.weightliftingbuddy.data.models.Workout
import com.example.weightliftingbuddy.data.room.dao.WorkoutDao
import com.example.weightliftingbuddy.utils.GeneralUtilities
import java.util.Date
import javax.inject.Inject

class WorkoutRepository @Inject constructor(private val workoutDao: WorkoutDao) {
    private var workoutList: MutableList<Workout> = mutableListOf()

    companion object {
        private const val LOGTAG = "WorkoutRepository"
    }

    suspend fun fetchWorkouts(): List<Workout> {
        val fetchedWorkoutList = workoutDao.fetchWorkouts()
        workoutList.apply {
            // Clear the existing workoutList
            clear()
            // Add all the new Workouts from fetchedWorkoutList
            addAll(fetchedWorkoutList)
        }
        return fetchedWorkoutList
    }

    suspend fun requestWorkoutInsertion(workout: Workout) {
        /**
         * Do not insert Workout if the passed Workout's workoutDate matches the workoutDate of a
         * Workout saved in Room. We want to only have one Workout per Date.
         */
        if (dateHasWorkout(workout.workoutDate)) {
            // Do not insert workout
            Log.i(LOGTAG, "Workout insertion aborted. Current date already has a workout")
        } else {
            Log.i(LOGTAG, "Inserting the Workout: $workout")
            workoutDao.insertWorkout(workout)
        }
    }

    /**
     * Checks if the passed Date matches the workoutDate of a Workout saved in Room.
     */
    fun dateHasWorkout(workoutDate: Date): Boolean {
        // Try to find a Workout with the same workoutDate as the passed workout
        val workoutSavedInRoom = findWorkout(this.workoutList, workoutDate)
        return workoutSavedInRoom != null
    }

    suspend fun deleteWorkout(workout: Workout) {
        workoutDao.deleteWorkout(workout)
    }

    suspend fun updateWorkout(workout: Workout) {
        Log.i(LOGTAG, "Updating the Workout: $workout")
        workoutDao.updateWorkout(workout)
    }

    /**
     * Searches the passed List for a Workout that has the passed Date
     * If no Workout is found, null will be returned.
     *
     * @param workoutList The List that will be searched
     * @param selectedDate The Date that will be looked for
     */
    fun findWorkout(workoutList: List<Workout>?, selectedDate: Date?): Workout? {
        if (workoutList == null || selectedDate == null) {
            return null
        }
        val formattedSelectedDate = GeneralUtilities.getFormattedWorkoutDate(selectedDate)
        workoutList.forEach {
            val formattedCurrentIterationDate = GeneralUtilities.getFormattedWorkoutDate(it.workoutDate)

            if (formattedSelectedDate == formattedCurrentIterationDate) {
                return it
            }
        }
        return null
    }

}