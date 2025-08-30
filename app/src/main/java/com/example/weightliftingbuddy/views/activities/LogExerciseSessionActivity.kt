package com.example.weightliftingbuddy.views.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.weightliftingbuddy.data.models.Exercise
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
                LogExerciseSessionComposable()
            }
        }
    }

    @Composable
    fun LogExerciseSessionComposable() {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TeamInfoScreenTopAppBar(viewModel.exerciseSession.value?.exercise)
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ) {
                var textA by remember { mutableStateOf("") }
                TextField(
                    value =  textA,
                    onValueChange = {
                        textA = it
                    }
                )
            }
        }
    }

    private fun getExerciseSessionFromArguments(): ExerciseSession? {
        var exerciseSession: ExerciseSession? = null
        intent?.apply {
            extras?.apply {
                exerciseSession = getParcelable(EXERCISE_TO_LOG_BUNDLE_KEY)
            }
        }
        return exerciseSession
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeamInfoScreenTopAppBar(exercise: Exercise?) {
    TopAppBar(
        title = {
            exercise?.exerciseName?.apply {
                Text(this)
            }
        }
    )
}
