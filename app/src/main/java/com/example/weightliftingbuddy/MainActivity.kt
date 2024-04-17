package com.example.weightliftingbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weightliftingbuddy.databinding.LayoutHomePageBinding
import com.example.weightliftingbuddy.ui.theme.WeightliftingBuddyTheme

class MainActivity : ComponentActivity() {
    private var viewModel: MainActivityViewModel? = null
    private var binding: LayoutHomePageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding = LayoutHomePageBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
        }
    }

    override fun onStart() {
        super.onStart()
        binding?.apply {
            this.logWorkoutScreenButton.setOnClickListener(logWorkoutOnClick)
        }
    }

    private val logWorkoutOnClick: OnClickListener = OnClickListener {
        val intent = Intent(this, LogExerciseActivity::class.java)
        startActivity(intent)
    }
}