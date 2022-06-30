package com.ab.savepass.ui.fragments.about

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ab.core.constants.UPDATE_PASSWORD
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentAboutBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {

    private val binding: FragmentAboutBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonChangePassword.setOnClickListener {
                navigateToChangePassword()
            }
            buttonDeleteAll.setOnClickListener {
                navigateToDeleteAll()
            }
        }
    }

    private fun navigateToDeleteAll() {
        findNavController().navigate(
            AboutFragmentDirections.actionAboutFragmentToDeleteAllDialogFragment()
        )
    }

    private fun navigateToChangePassword() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)

        findNavController().navigate(
            AboutFragmentDirections.actionAboutFragmentToWelcomeFragment(
                request = UPDATE_PASSWORD
            )
        )
    }

}