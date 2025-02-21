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

    suspend fun insertWorkout(workout: Workout) {
        if (shouldInsertWorkout(workout)) {
            Log.i(LOGTAG, "Inserting the Workout: $workout")
            workoutDao.insertWorkout(workout)
        } else {
            // Do not insert workout
            Log.i(LOGTAG, "Workout insertion aborted")
        }
    }

    /**
     * Checks if the passed Workout should be inserted.
     * If the passed Workout's workoutDate has the same Date as a Workout's workoutDate that is
     * saved in Room, the passed Workout should not be inserted.
     * Essentially, there should not be two Workouts saved in Room with the same Date because we
     * want to limit the number of Workouts to one per Date.
     */
    private fun shouldInsertWorkout(workout: Workout): Boolean {
        // Try to find a Workout with the same workoutDate as the passed workout
        val workoutSavedInRoom = findWorkout(this.workoutList, workout.workoutDate)

        // If workoutSavedInRoom is null, the insertion can be done.
        return workoutSavedInRoom == null
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