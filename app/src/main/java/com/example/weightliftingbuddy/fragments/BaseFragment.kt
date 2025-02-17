package com.example.weightliftingbuddy.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        initBinding()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    abstract fun initBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        setOnClickListeners()
    }

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
}