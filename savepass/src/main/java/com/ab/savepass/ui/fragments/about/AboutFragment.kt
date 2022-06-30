package com.ab.savepass.ui.fragments.about

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ab.core.constants.IS_ENABLE_FINGER
import com.ab.core.constants.UPDATE_PASSWORD
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentAboutBinding
import com.ab.savepass.util.FingerPrintHelperClass
import com.ab.savepass.util.openCustomChromeTab
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AboutFragment : Fragment(R.layout.fragment_about) {

    private val binding: FragmentAboutBinding by viewBinding()

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var fingerPrintHelperClass: FingerPrintHelperClass

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
            buttonAcknowledgement.setOnClickListener {
                navigateToAcknowledgement()
            }
            buttonVersion.setOnClickListener {
                navigateToVersion()
            }
            buttonDev.setOnClickListener {
                navigateToDev()
            }
            switchFingerPrint.apply {
                isEnabled = fingerPrintHelperClass.isBiometricSupport()
                setOnCheckedChangeListener(null)
                setUpSwitch()
                setOnCheckedChangeListener { _, isChecked ->
                    fingerPrintHelperClass.openBiometricPrompt()
                    fingerPrintHelperClass.setAction {
                        pref.edit().putBoolean(IS_ENABLE_FINGER, isChecked).apply()
                    }
                }
            }
            setUpSwitch()
        }

    }

    private fun navigateToDev() {
        requireContext().openCustomChromeTab(resources.getString(R.string.ayaan_link))
    }

    private fun navigateToVersion() {
        requireContext().openCustomChromeTab(resources.getString(R.string.app_link))
    }

    private fun navigateToAcknowledgement() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        findNavController().navigate(AboutFragmentDirections.actionAboutFragmentToAcknowledgementFragment())
    }

    private fun setUpSwitch() {
        val isEnableFinger = pref.getBoolean(IS_ENABLE_FINGER, false)
        binding.switchFingerPrint.isChecked = isEnableFinger
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