package com.ab.savepass.ui.fragments.welcome

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ab.core.constants.PREF_PASSWORD
import com.ab.core.constants.UPDATE_PASSWORD
import com.ab.core.repositories.BcryptRepository
import com.ab.savepass.R
import com.ab.savepass.databinding.LayoutWelcomeBinding
import com.ab.savepass.ui.activity.main_activity.MainActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WelcomeFragment : Fragment(R.layout.layout_welcome) {
    private val binding: LayoutWelcomeBinding by viewBinding()
    private val arg: WelcomeFragmentArgs by navArgs()

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var bcryptRepository: BcryptRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkSavePass()
        binding.apply {
            outlinedTextFieldPasswordConfirm.editText?.addTextChangedListener {
                if (it.toString() == outlinedTextFieldPassword.editText?.text.toString()) {
                    outlinedTextFieldPasswordConfirm.error = null
                    buttonSetUpPassword.isVisible = true
                } else {
                    outlinedTextFieldPasswordConfirm.error = getString(R.string.password_not_match)
                    buttonSetUpPassword.isVisible = false
                }
            }
            buttonSetUpPassword.setOnClickListener {
                bcryptRepository.hash(outlinedTextFieldPassword.editText?.text.toString())
                    .also {
                        pref.edit().putString(PREF_PASSWORD, it).apply()
                        findNavController().navigate(WelcomeFragmentDirections.actionWelcomeFragmentToHomeFragment())
                    }
            }
            imageViewWelcome.isVisible = arg.request != UPDATE_PASSWORD
            textViewSetUpPassword.isVisible = arg.request != UPDATE_PASSWORD
            if (arg.request == UPDATE_PASSWORD)
                (activity as MainActivity).findViewById<AppBarLayout>(R.id.appbar_layout).isVisible =
                    true
        }
    }

    private fun checkSavePass() {
        if (pref.getString(PREF_PASSWORD, "") != "" && arg.request != UPDATE_PASSWORD) {
            findNavController().navigate(
                WelcomeFragmentDirections.actionWelcomeFragmentToCheckPasswordFragment()
            )
        }
    }
}