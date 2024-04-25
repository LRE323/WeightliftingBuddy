package com.example.weightliftingbuddy.fragments

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
import com.example.weightliftingbuddy.databinding.FragmentExerciseListBinding
import com.example.weightliftingbuddy.dialogfragments.AddNewExerciseDialog
import com.example.weightliftingbuddy.models.Exercise
import com.example.weightliftingbuddy.room.database.ExerciseDatabase
import com.example.weightliftingbuddy.viewmodels.ExerciseListViewModel

class ExerciseListFragment : Fragment(), AddNewExerciseDialog.AddNewExerciseCallBack {
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
        adapterExerciseList = ExerciseListAdapter()
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
        }
    }

    private fun setOnClickListeners() {
        binding?.apply {
            fabCreateExercise.setOnClickListener {
                AddNewExerciseDialog(requireContext(), this@ExerciseListFragment).show()
            }
        }
    }

    override fun onCreateExerciseSuccess(addNewExerciseDialog: AlertDialog,
                                         exerciseCreated: Exercise) {
        addNewExerciseDialog.dismiss()
        viewModel?.saveExercise(exerciseCreated)
    }
}