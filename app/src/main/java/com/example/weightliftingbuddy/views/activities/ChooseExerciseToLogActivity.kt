package com.example.weightliftingbuddy.views.activities

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.adapters.ExerciseListAdapter
import com.example.weightliftingbuddy.databinding.ActivityChooseExerciseToLogBinding
import com.example.weightliftingbuddy.views.fragments.SelectedDateOverviewFragment
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.viewmodels.ChooseExerciseToLogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseExerciseToLogActivity : AppCompatActivity(), ExerciseListAdapter.OnClickExerciseCallBack {
    private var binding: ActivityChooseExerciseToLogBinding? = null
    private val viewModel: ChooseExerciseToLogViewModel by viewModels()

    // RecyclerView stuff
    private var recyclerViewExerciseList: RecyclerView? = null
    private var adapterExerciseList: ExerciseListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    private fun initBinding() {
        binding = ActivityChooseExerciseToLogBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(root)
        }
    }

    override fun onStart() {
        super.onStart()
        // Set the title for the action bar.
        supportActionBar?.title = getString(R.string.title_choose_exercise_to_log_activity)
        initBinding()
    }

    private fun initObservers() {
        viewModel.apply {
            exerciseList.observe(this@ChooseExerciseToLogActivity){
                initRecyclerView(it.getContentIfNotHandled())
            }
        }
    }

    private fun initRecyclerView(createdExercises: List<Exercise>?) {
        // Init the adapter.
        adapterExerciseList = ExerciseListAdapter(createdExercises)
        adapterExerciseList?.onClickExerciseCallBack = this

        // Init the RecyclerView.
        recyclerViewExerciseList = binding?.rvExerciseList
        recyclerViewExerciseList?.apply {
            adapter = adapterExerciseList
            layoutManager = LinearLayoutManager(this@ChooseExerciseToLogActivity)
        }
    }

    override fun onClickExercise(exerciseClicked: Exercise, position: Int) {
        intent.putExtra(SelectedDateOverviewFragment.SELECTED_EXERCISE, exerciseClicked)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}