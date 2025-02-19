package com.example.weightliftingbuddy.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        return getBinding()?.root
    }

    abstract fun initBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initViews()
        setOnClickListeners()
    }

    abstract fun initViews()

    override fun onDestroyView() {
        super.onDestroyView()
        setBindingNull()
    }

    abstract fun setOnClickListeners()

    abstract fun initObservers()

    /**
     * Called in onDestroyView to prevent memory leaks
     */
    abstract fun setBindingNull()

    abstract fun getBinding(): ViewDataBinding?
}