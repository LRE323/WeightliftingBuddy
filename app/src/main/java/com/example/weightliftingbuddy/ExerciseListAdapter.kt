package com.example.weightliftingbuddy

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.models.Exercise

class ExerciseListAdapter(private var exerciseList: List<Exercise>? = null,
                          var onLongClickExerciseCallBack: OnLongClickExerciseCallBack? = null,
                          var onClickExerciseCallBack: OnClickExerciseCallBack? = null
): RecyclerView.Adapter<ExerciseListAdapter.ExerciseListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseListViewHolder {
        val viewExerciseItem = LayoutInflater.from(parent.context).inflate(R.layout.item_exercise, parent, false)
        return ExerciseListViewHolder(viewExerciseItem)
    }

    override fun getItemCount(): Int {
        return exerciseList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ExerciseListViewHolder, position: Int) {
        exerciseList?.apply {
            val currentExercise = get(position)
            setExerciseName(holder.itemView ,currentExercise)

            // Set the OnClickListeners
            onClickExerciseCallBack?.apply {
                holder.itemView.setOnClickListener { onClickExercise(currentExercise, position) }
            }
            onLongClickExerciseCallBack?.apply {
                holder.itemView.setOnLongClickListener { onLongClickExercise(currentExercise, position); true}
            }
        }
    }

    private fun setExerciseName(view: View, exercise: Exercise) {
        val textViewExerciseName = view.findViewById<TextView>(R.id.exercise_item_exercise_name)
        textViewExerciseName.text = exercise.exerciseName
    }

    fun updateList(newExerciseList: List<Exercise>) {
        exerciseList = newExerciseList
        notifyDataSetChanged()
    }

    interface OnLongClickExerciseCallBack {
        fun onLongClickExercise(exerciseClicked: Exercise, position: Int)
    }

    interface OnClickExerciseCallBack {
        fun onClickExercise(exerciseClicked: Exercise, position: Int)
    }

    inner class ExerciseListViewHolder(exerciseView: View): RecyclerView.ViewHolder(exerciseView)
}