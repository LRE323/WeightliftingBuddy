package com.example.weightliftingbuddy.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.databinding.LayoutActivityHomeBinding
import com.example.weightliftingbuddy.views.fragments.ExerciseListFragment
import com.example.weightliftingbuddy.views.fragments.HistoryFragment
import com.example.weightliftingbuddy.views.fragments.SelectedDateOverviewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private var binding: LayoutActivityHomeBinding? = null
    private var bottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = LayoutActivityHomeBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
            bottomNavigationView = bottomNavigation
        }
        val homeFragment = SelectedDateOverviewFragment()
        if (supportFragmentManager.fragments.isEmpty()) {
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit()
            setSupportActionBarTitle(homeFragment)
        }
    }

    override fun onStart() {
        super.onStart()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        bottomNavigationView?.setOnItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragmentToShow: Fragment? = null
        // Identify the Fragment selected.
        when (item.itemId) {
            R.id.menuItemHome -> {
                fragmentToShow = SelectedDateOverviewFragment()
                supportActionBar?.title = getString(R.string.menu_option_name_home)
            }

            R.id.menuItemExerciseList -> {
                fragmentToShow = ExerciseListFragment()
                supportActionBar?.title = getString(R.string.menu_option_name_exercises)
            }

            R.id.menuItemHistory -> {
                fragmentToShow = HistoryFragment()
                supportActionBar?.title = getString(R.string.menu_option_name_history)
            }
        }
        fragmentToShow?.apply {
            // Show the Fragment that was selected.
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, this).commit()
            setSupportActionBarTitle(this)
        }
        return fragmentToShow != null
    }

    private fun setSupportActionBarTitle(fragmentShowing: Fragment) {
        val titleToSet = when (fragmentShowing) {
            is SelectedDateOverviewFragment -> {
                getString(R.string.menu_option_name_home)
            }

            is ExerciseListFragment -> {
                getString(R.string.menu_option_name_exercises)
            }

            is HistoryFragment -> {
                getString(R.string.menu_option_name_history)
            }

            else -> {
                ""
            }
        }
        supportActionBar?.title = titleToSet
    }
}