package com.example.weightliftingbuddy.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.example.weightliftingbuddy.ui.theme.WeightliftingBuddyTheme

class LogExerciseSessionActivity: ComponentActivity() {
    private val viewModel = LogExerciseSessionViewModel()

    companion object {
        const val EXERCISE_TO_LOG_BUNDLE_KEY = "EXERCISE_TO_LOG_BUNDLE_KEY"
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initData(getExerciseSessionFromArguments())
        setContent {
            WeightliftingBuddyTheme {

            }
        }
    }

    @Composable
    fun LogExerciseSessionComposable() {
    }

    private fun getExerciseSessionFromArguments(): ExerciseSession? {
        var exerciseSession: ExerciseSession? = null
        intent?.apply {
            extras?.apply {
                exerciseSession = getParcelable(EXERCISE_TO_LOG_BUNDLE_KEY)
            }
        }
        return null
    }
}
