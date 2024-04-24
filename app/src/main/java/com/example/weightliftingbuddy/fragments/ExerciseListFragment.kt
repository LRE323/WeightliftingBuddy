package com.example.weightliftingbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.weightliftingbuddy.databinding.FragmentExerciseListBinding
import com.example.weightliftingbuddy.dialogfragments.AddNewExerciseDialog
import com.example.weightliftingbuddy.models.Exercise

class ExerciseListFragment : Fragment(), AddNewExerciseDialog.AddNewExerciseCallBack {
    private var binding: FragmentExerciseListBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentExerciseListBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewVariables()
    }

    private fun initViewVariables() {
        binding?.apply {
            layoutNoExercisesAdded.btnAddNewExercise.setOnClickListener {
                AddNewExerciseDialog(requireContext(), this@ExerciseListFragment).show()
            }
        }
    }

    override fun onCreateExerciseSuccess(addNewExerciseDialog: AlertDialog,
                                         exerciseCreated: Exercise) {
        addNewExerciseDialog.dismiss()
    }
}