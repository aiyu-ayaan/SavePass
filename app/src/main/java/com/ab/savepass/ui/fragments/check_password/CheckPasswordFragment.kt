package com.ab.savepass.ui.fragments.check_password

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ab.core.constants.PREF_PASSWORD
import com.ab.core.repositories.BcryptRepository
import com.ab.savepass.R
import com.ab.savepass.databinding.LayoutCheckPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class CheckPasswordFragment : Fragment(R.layout.layout_check_password) {
    private val binding: LayoutCheckPasswordBinding by viewBinding()

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var bcryptRepository: BcryptRepository

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
                            findNavController().navigate(CheckPasswordFragmentDirections.actionCheckPasswordFragmentToHomeFragment())
                        else
                            binding.outlinedTextFieldPassword.error =
                                getString(R.string.password_not_match)
                    }
            }
        }
    }

    private fun LayoutCheckPasswordBinding.checkPassword() = lifecycleScope.launchWhenCreated {
        val isTrue = bcryptRepository.check(
            binding.editTextPassword.text.toString(),
            pref.getString(PREF_PASSWORD, "")!!
        )
        if (isTrue)
            findNavController().navigate(CheckPasswordFragmentDirections.actionCheckPasswordFragmentToHomeFragment())
        else
            binding.outlinedTextFieldPassword.error =
                getString(R.string.password_not_match)
    }
}