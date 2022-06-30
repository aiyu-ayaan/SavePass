package com.ab.savepass.ui.fragments.check_password

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ab.core.constants.IS_ENABLE_FINGER
import com.ab.core.constants.PREF_PASSWORD
import com.ab.core.constants.REQUEST_VERIFY_DONE
import com.ab.core.constants.REQUEST_VERIFY_PIN
import com.ab.core.repositories.BcryptRepository
import com.ab.savepass.R
import com.ab.savepass.databinding.LayoutCheckPasswordBinding
import com.ab.savepass.ui.activity.main_activity.CommunicatorViewModel
import com.ab.savepass.util.FingerPrintHelperClass
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CheckPasswordFragment : Fragment(R.layout.layout_check_password) {
    private val binding: LayoutCheckPasswordBinding by viewBinding()

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var bcryptRepository: BcryptRepository

    @Inject
    lateinit var fingerPrintHelperClass: FingerPrintHelperClass

    private val args: CheckPasswordFragmentArgs by navArgs()
    private val communicatorViewModel by activityViewModels<CommunicatorViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonContinue.setOnClickListener {
                checkPassword()
            }
            binding.editTextPassword.addTextChangedListener {
                if (it.toString().length == 4)
                    lifecycleScope.launchWhenCreated {
                        val isTrue = bcryptRepository.check(
                            it.toString(),
                            pref.getString(PREF_PASSWORD, "")!!
                        )
                        if (isTrue)
                            if (args.reuqest != REQUEST_VERIFY_PIN)
                                navigateToHome()
                            else
                                navigateToDetail()
                        else
                            binding.outlinedTextFieldPassword.error =
                                getString(R.string.password_not_match)
                    }
            }
        }
        checkBiometricIsEnable()
    }

    private fun navigateToDetail() {
        communicatorViewModel.isAuthenticated.value = true
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        findNavController().navigate(
            CheckPasswordFragmentDirections.actionCheckPasswordFragmentToDetailFragment(
                args.passwordModel!!,
                REQUEST_VERIFY_DONE
            )
        )
    }

    private fun checkBiometricIsEnable() {
        pref.getBoolean(IS_ENABLE_FINGER, false).let {
            if (it)
                setUpBiometricPrompt()
        }
    }

    private fun setUpBiometricPrompt() {
        fingerPrintHelperClass.openBiometricPrompt()
        fingerPrintHelperClass.setAction {
            if (args.reuqest != REQUEST_VERIFY_PIN)
                navigateToHome()
            else
                navigateToDetail()
        }
    }

    private fun LayoutCheckPasswordBinding.checkPassword() = lifecycleScope.launchWhenCreated {
        val isTrue = bcryptRepository.check(
            binding.editTextPassword.text.toString(),
            pref.getString(PREF_PASSWORD, "")!!
        )
        if (isTrue)
            navigateToHome()
        else
            binding.outlinedTextFieldPassword.error =
                getString(R.string.password_not_match)
    }

    private fun navigateToHome() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        findNavController().navigate(CheckPasswordFragmentDirections.actionCheckPasswordFragmentToHomeFragment())
    }
}