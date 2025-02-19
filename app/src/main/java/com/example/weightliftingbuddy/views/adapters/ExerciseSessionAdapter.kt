package com.example.weightliftingbuddy.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.data.models.Exercise
import com.example.weightliftingbuddy.data.models.ExerciseSession
import com.example.weightliftingbuddy.databinding.RvItemExerciseSessionBinding

class ExerciseSessionAdapter(
    private val exerciseSessionList: List<ExerciseSession>,
    private val callback: ExerciseSessionAdapterCallback
) : RecyclerView.Adapter<ExerciseSessionAdapter.ExerciseSessionViewHolder>() {

    var binding: RvItemExerciseSessionBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseSessionViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvItemExerciseSessionBinding.inflate(layoutInflater, parent, false)
        this.binding = binding
        return ExerciseSessionViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return exerciseSessionList.size
    }

    override fun onBindViewHolder(holder: ExerciseSessionViewHolder, position: Int) {
        val context = holder.itemView.context
        val currentExerciseSession = exerciseSessionList[position]
        val currentExercise = currentExerciseSession.exercise

        setExerciseNameText(currentExercise)
        setNumberOfSetsText(currentExerciseSession, context)
        setOnClickListeners(holder.itemView,currentExerciseSession)
    }

    private fun setOnClickListeners(
        exerciseSessionView: View, currentExerciseSession: ExerciseSession
    ) {
        exerciseSessionView.apply {
            setOnClickListener {
                callback.onClickExerciseSession(currentExerciseSession)
            }
            setOnLongClickListener {
                callback.onLongClickExerciseSession(currentExerciseSession)
                true
            }
        }
    }

    private fun setNumberOfSetsText(exerciseSession: ExerciseSession, context: Context) {
        val listOfExerciseSets = exerciseSession.listOfExerciseSets
        val numberOfSetsLogged = listOfExerciseSets?.size ?: 0
        binding?.apply {
            tvNumberOfSets.text = context.getString(R.string.rv_item_exercise_session_sets_logged, numberOfSetsLogged.toString())
        }
    }

    private fun setExerciseNameText(exercise: Exercise?) {
        binding?.apply {
            tvExerciseName.text = exercise?.exerciseName
        }
    }

    inner class ExerciseSessionViewHolder(binding: RvItemExerciseSessionBinding) : RecyclerView.ViewHolder(binding.root)

    interface ExerciseSessionAdapterCallback {
        fun onClickExerciseSession(exerciseSession: ExerciseSession)
        fun onLongClickExerciseSession(exerciseSession: ExerciseSession)
    }
}