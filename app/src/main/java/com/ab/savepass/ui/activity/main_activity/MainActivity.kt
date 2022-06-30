package com.ab.savepass.ui.activity.main_activity

import android.content.SharedPreferences
import android.os.Bundle
import android.viewbinding.library.activity.viewBinding
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ab.core.constants.PREF_PASSWORD
import com.ab.savepass.NavGraphDirections
import com.ab.savepass.R
import com.ab.savepass.databinding.ActivityMainBinding
import com.ab.savepass.util.currentNavigationFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.apply {
            setSupportActionBar(toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.homeFragment,
                    R.id.checkPasswordFragment,
                )
            )

            collapsingToolbar.setupWithNavController(toolbar, navController, appBarConfiguration)
            setupActionBarWithNavController(navController, appBarConfiguration)

            fabAddPassword.setOnClickListener {
                navigateToDialogFragment()
            }
        }
        onDestinationChange()
    }

    private fun onDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> {
                    binding.fabAddPassword.show()
                }
                else -> {
                    binding.fabAddPassword.hide()
                }
            }
            when (destination.id) {
                R.id.welcomeFragment, R.id.checkPasswordFragment -> {
                    binding.appbarLayout.isVisible = false
                }
                else -> {
                    binding.appbarLayout.isVisible = true
                }
            }
        }
    }

    private fun navigateToDialogFragment() {
        val action = NavGraphDirections.actionGlobalAddUpdatePasswordDialogFragment()
        navController.navigate(action)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun getCurrentFragment(): Fragment? =
        supportFragmentManager.currentNavigationFragment

    override fun onPause() {
        super.onPause()
        if (pref.getString(PREF_PASSWORD, "") != "") {
            getCurrentFragment()?.let {
                navController.navigate(
                    NavGraphDirections.actionGlobalCheckPasswordFragment()
                )
            }
        }
    }
}