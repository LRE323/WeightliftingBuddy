package com.example.weightliftingbuddy

import java.text.SimpleDateFormat
import java.util.Date

class GeneralUtilities {

    companion object {
        private const val DEFAULT_WORKOUT_DATE_FORMAT = "EEE MMM dd"
        fun getFormattedWorkoutDate(workoutDate: Date): String {
            val simpleDateFormat = SimpleDateFormat(DEFAULT_WORKOUT_DATE_FORMAT)
            return simpleDateFormat.format(workoutDate)
        }
    }
}