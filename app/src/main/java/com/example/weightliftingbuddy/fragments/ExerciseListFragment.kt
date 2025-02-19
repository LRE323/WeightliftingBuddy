package com.example.weightliftingbuddy.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.adapters.ExerciseListAdapter
import com.example.weightliftingbuddy.databinding.FragmentExerciseListBinding
import com.example.weightliftingbuddy.dialogfragments.AddNewExerciseDialog
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.viewmodels.Event
import com.example.weightliftingbuddy.viewmodels.ExerciseListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExerciseListFragment : BaseFragment(), AddNewExerciseDialog.AddNewExerciseCallBack, ExerciseListAdapter.OnLongClickExerciseCallBack {
    private var binding: FragmentExerciseListBinding? = null
    private val viewModel: ExerciseListViewModel by viewModels()

    // RecyclerView stuff
    private var recyclerViewExerciseList: RecyclerView? = null
    private var adapterExerciseList: ExerciseListAdapter? = null

    override fun initBinding() {
        binding = FragmentExerciseListBinding.inflate(layoutInflater)    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchExercises()
    }

    override fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapterExerciseList = ExerciseListAdapter(onLongClickExerciseCallBack = this)
        recyclerViewExerciseList = binding?.recyclerViewExerciseList
        recyclerViewExerciseList?.apply {
            adapter = adapterExerciseList
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun initObservers() {
        viewModel.apply {
            exerciseList.observe(viewLifecycleOwner) {
                binding?.apply {
                    val noExercisesAddedLayout = layoutNoExercisesAdded.parent
                    if (it.isEmpty()) {
                        noExercisesAddedLayout.visibility = View.VISIBLE
                        recyclerViewExerciseList.visibility = View.GONE
                    } else {
                        noExercisesAddedLayout.visibility = View.GONE
                        recyclerViewExerciseList.visibility = View.VISIBLE
                    }
                }
                adapterExerciseList?.updateList(it)
            }
            onDeleteExerciseSuccess.observe(viewLifecycleOwner, onDeleteExerciseObserver)
            onCreateNewExerciseSuccess.observe(viewLifecycleOwner, onCreateNewExerciseSuccessObserver)
        }
    }

    override fun setBindingNull() {
        binding = null
    }

    override fun getBinding(): ViewDataBinding? {
        return binding
    }

    private val onCreateNewExerciseSuccessObserver = Observer<Event<Exercise>> {
        it.getContentIfNotHandled()?.apply {
            val exerciseCreated = this
            view?.apply {
                Snackbar.make(this,getString(R.string.snackbar_msg_create_exercise_success, exerciseCreated.exerciseName), Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private val onDeleteExerciseObserver = Observer<Event<Boolean>> {
        it.getContentIfNotHandled()?.apply {
            view?.apply {
                Snackbar.make(this, getString(R.string.snack_bar_msg_deleted_exercise, viewModel.exerciseToDelete?.exerciseName), Snackbar.LENGTH_SHORT).show()
            }
            viewModel.fetchExercises()
            viewModel.exerciseToDelete = null
        }
    }

    override fun setOnClickListeners() {
        binding?.apply {
            fabCreateExercise.setOnClickListener {
                AddNewExerciseDialog(requireContext(), this@ExerciseListFragment).show()
            }
        }
    }

    override fun onValidateNewExerciseSuccess(addNewExerciseDialog: AlertDialog,
                                              exerciseCreated: Exercise) {
        addNewExerciseDialog.dismiss()
        viewModel.saveExercise(exerciseCreated)
    }

    override fun onLongClickExercise(exerciseClicked: Exercise, position: Int) {
        viewModel.apply {
            exerciseToDelete = exerciseClicked
            getConfirmDeleteExerciseDialog().show()
        }
    }

    private fun getConfirmDeleteExerciseDialog(): AlertDialog {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
        dialogBuilder.apply {
            val exerciseToDeleteName = viewModel.exerciseToDelete?.exerciseName
            setTitle(getString(R.string.confirm_delete_exercise_dialog_title))
            setMessage(exerciseToDeleteName)
            setPositiveButton(getString(R.string.confirm_delete_exercise_dialog_positive_button), onConfirmDeleteExercise)
            setNegativeButton(getString(R.string.confirm_delete_exercise_dialog_negative_button), onCancelDeleteExercise)
        }
        return dialogBuilder.create()
    }

    private val onCancelDeleteExercise = DialogInterface.OnClickListener{_, _ ->
        viewModel.exerciseToDelete = null
    }

    private val onConfirmDeleteExercise =
        DialogInterface.OnClickListener { _, _ ->
            viewModel.apply {
                deleteExercise(exerciseToDelete)
            }
        }
}