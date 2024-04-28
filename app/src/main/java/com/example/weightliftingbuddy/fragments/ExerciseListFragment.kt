package com.example.weightliftingbuddy.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.weightliftingbuddy.ExerciseListAdapter
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.databinding.FragmentExerciseListBinding
import com.example.weightliftingbuddy.dialogfragments.AddNewExerciseDialog
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.database.ExerciseDatabase
import com.example.weightliftingbuddy.viewmodels.ExerciseListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ExerciseListFragment : Fragment(), AddNewExerciseDialog.AddNewExerciseCallBack, ExerciseListAdapter.OnClickExerciseListener {
    private var binding: FragmentExerciseListBinding? = null
    private var exerciseDatabase: ExerciseDatabase? = null
    private var lazyViewModel: Lazy<ExerciseListViewModel>? = null
    private var viewModel: ExerciseListViewModel? = null

    // RecyclerView stuff
    private var recyclerViewExerciseList: RecyclerView? = null
    private var adapterExerciseList: ExerciseListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.apply {
            exerciseDatabase = Room.databaseBuilder(applicationContext, ExerciseDatabase::class.java, "exercise.db").build()
        }
        initViewModel()
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
        initViews()
        initObservers()
        viewModel?.fetchExercises()
    }

    private fun initViews() {
        initRecyclerView()
        setOnClickListeners()
    }

    private fun initRecyclerView() {
        adapterExerciseList = ExerciseListAdapter(onClickExerciseListener = this)
        recyclerViewExerciseList = binding?.recyclerViewExerciseList
        recyclerViewExerciseList?.apply {
            adapter = adapterExerciseList
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun initViewModel() {
        lazyViewModel = activity?.viewModels<ExerciseListViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return exerciseDatabase?.dao?.let { ExerciseListViewModel(it) } as T
                }
            }
        })
        viewModel = lazyViewModel?.value
    }

    private fun initObservers() {
        viewModel?.apply {
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

            onDeleteExerciseSuccess.observe(viewLifecycleOwner) {
                view?.apply {
                    Snackbar.make(this, getString(R.string.snack_bar_msg_deleted_exercise, viewModel?.exerciseToDelete?.exerciseName), Snackbar.LENGTH_SHORT).show()
                }
                viewModel?.fetchExercises()
                viewModel?.exerciseToDelete = null
            }

            onCreateNewExerciseSuccess.observe(viewLifecycleOwner) {
                view?.apply {
                    Snackbar.make(this,getString(R.string.snackbar_msg_create_exercise_success, it.exerciseName), Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            fabCreateExercise.setOnClickListener {
                AddNewExerciseDialog(requireContext(), this@ExerciseListFragment).show()
            }
        }
    }

    override fun onValidateNewExerciseSuccess(addNewExerciseDialog: AlertDialog,
                                              exerciseCreated: Exercise) {
        addNewExerciseDialog.dismiss()
        viewModel?.saveExercise(exerciseCreated)
    }

    override fun onLongClickExercise(exerciseClicked: Exercise, position: Int) {
        viewModel?.apply {
            exerciseToDelete = exerciseClicked
            getConfirmDeleteExerciseDialog().show()
        }
    }

    private fun getConfirmDeleteExerciseDialog(): AlertDialog {
        val dialogBuilder = MaterialAlertDialogBuilder(requireContext())
        dialogBuilder.apply {
            val exerciseToDeleteName = viewModel?.exerciseToDelete?.exerciseName
            setTitle(getString(R.string.confirm_delete_exercise_dialog_title))
            setMessage(exerciseToDeleteName)
            setPositiveButton(getString(R.string.confirm_delete_exercise_dialog_positive_button), onConfirmDeleteExercise)
            setNegativeButton(getString(R.string.confirm_delete_exercise_dialog_negative_button), onCancelDeleteExercise)
        }
        return dialogBuilder.create()
    }

    private val onCancelDeleteExercise = DialogInterface.OnClickListener{_, _ ->
        viewModel?.exerciseToDelete = null
    }

    private val onConfirmDeleteExercise =
        DialogInterface.OnClickListener { _, _ ->
            viewModel?.apply {
                deleteExercise(exerciseToDelete)
            }
        }
}