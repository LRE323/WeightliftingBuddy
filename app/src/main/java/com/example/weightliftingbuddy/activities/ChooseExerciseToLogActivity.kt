package com.example.weightliftingbuddy.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.models.Exercise

class ChooseExerciseToLogActivity : AppCompatActivity() {

    companion object {
        private const val EXERCISE_LIST = "EXERCISE_LIST"

        fun getIntent(context: Context, exerciseList: ArrayList<Exercise>): Intent {
            val intent = Intent(context, ChooseExerciseToLogActivity::class.java)
            intent.putParcelableArrayListExtra(EXERCISE_LIST, exerciseList)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_exercise2)
    }
}