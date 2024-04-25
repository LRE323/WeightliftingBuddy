package com.example.weightliftingbuddy.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.weightliftingbuddy.R
import com.example.weightliftingbuddy.databinding.LayoutActivityHomeBinding
import com.example.weightliftingbuddy.fragments.ExerciseListFragment
import com.example.weightliftingbuddy.fragments.HistoryFragment
import com.example.weightliftingbuddy.fragments.SelectedDateOverviewFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

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
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit()
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
            }

            R.id.menuItemExerciseList -> {
                fragmentToShow = ExerciseListFragment()
            }

            R.id.menuItemHistory -> {
                fragmentToShow = HistoryFragment()
            }
        }
        fragmentToShow?.apply {
            // Show the Fragment that was selected.
            supportFragmentManager.beginTransaction().replace(R.id.frameLayout, this).commit()
        }
        return fragmentToShow != null
    }
}