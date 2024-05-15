package com.example.weightliftingbuddy.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.ExerciseListAdapter
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.databinding.ActivityChooseExerciseToLogBinding
import com.example.weightliftingbuddy.fragments.SelectedDateOverviewFragment
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.viewmodels.ChooseExerciseToLogViewModel

class ChooseExerciseToLogActivity : AppCompatActivity(), ExerciseListAdapter.OnClickExerciseCallBack {
    private var binding: ActivityChooseExerciseToLogBinding? = null
    private var viewModel: ChooseExerciseToLogViewModel? = null

    // RecyclerView stuff
    private var recyclerViewExerciseList: RecyclerView? = null
    private var adapterExerciseList: ExerciseListAdapter? = null

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

        // Init the ViewModel.
        viewModel = ViewModelProvider(this)[ChooseExerciseToLogViewModel::class.java]
        viewModel?.init(getExerciseListFromExtras())

        // Init binding
        binding = ActivityChooseExerciseToLogBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(root)
        }

        // Set the title for the action bar.
        supportActionBar?.title = getString(R.string.title_choose_exercise_to_log_activity)
    }

    override fun onStart() {
        super.onStart()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        // Init the adapter.
        adapterExerciseList = ExerciseListAdapter()
        adapterExerciseList?.onClickExerciseCallBack = this

        // Init the RecyclerView.
        recyclerViewExerciseList = binding?.rvExerciseList
        recyclerViewExerciseList?.apply {
            adapter = adapterExerciseList
            layoutManager = LinearLayoutManager(this@ChooseExerciseToLogActivity)
        }

        // Update the exercise list.
        viewModel?.exerciseList?.apply {
            adapterExerciseList?.updateList(this)
        }
    }

    private fun getExerciseListFromExtras(): ArrayList<Exercise>? {
        return intent.getParcelableArrayListExtra(EXERCISE_LIST)
    }

    override fun onClickExercise(exerciseClicked: Exercise, position: Int) {
        intent.putExtra(SelectedDateOverviewFragment.SELECTED_EXERCISE, exerciseClicked)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}