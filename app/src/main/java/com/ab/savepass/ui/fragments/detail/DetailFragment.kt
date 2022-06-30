package com.ab.savepass.ui.fragments.detail

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ab.core.constants.REQUEST_VERIFY_DONE
import com.ab.core.constants.REQUEST_VERIFY_PIN
import com.ab.core.room.PasswordModel
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentDetailBinding
import com.ab.savepass.ui.activity.main_activity.CommunicatorViewModel
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel by viewModels<DetailViewModel>()
    private var isShowing: Boolean = true
    private val communicatorViewModel by activityViewModels<CommunicatorViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
    }

    private fun setView() = viewModel.passwordModel?.let { passwordModel ->
        binding.apply {
            outlinedTextFieldSite.editText?.setText(passwordModel.website)
            outlinedTextFieldUserName.editText?.setText(passwordModel.username)
            try {
                outlinedTextFieldPassword.editText?.setText(
                    viewModel.getPassword(
                        passwordModel.username,
                        passwordModel.password
                    )
                )
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                viewModel.deletePassword(passwordModel)
                findNavController().navigateUp()
            }

            buttonCancel.setOnClickListener {
                findNavController().navigateUp()
            }
            buttonSave.setOnClickListener {
                checkInput(passwordModel)
            }

            passwordToggle()
        }
        setHasOptionsMenu(true)
    }

    private fun FragmentDetailBinding.passwordToggle() {
//        After the user clicks the button, the password is hidden or shown.

        if (communicatorViewModel.isAuthenticated.value && viewModel.request == REQUEST_VERIFY_DONE) {
            outlinedTextFieldPassword.editText?.inputType =
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            Toast.makeText(requireContext(), "True", Toast.LENGTH_SHORT).show()
            isShowing = false
        }


        outlinedTextFieldPassword.setEndIconOnClickListener {
            if (isShowing && communicatorViewModel.isAuthenticated.value) {
                outlinedTextFieldPassword.editText?.inputType =
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                isShowing = false
            } else {
                if (!communicatorViewModel.isAuthenticated.value)
                    verifyUser()
                outlinedTextFieldPassword.editText?.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                isShowing = true
            }
        }
    }

    private fun verifyUser() {
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, /* forward= */ false)
        findNavController().navigate(
            DetailFragmentDirections.actionDetailFragmentToCheckPasswordFragment(
                REQUEST_VERIFY_PIN, viewModel.passwordModel
            )
        )
    }

    private fun checkInput(passwordModel: PasswordModel) {
        if (binding.outlinedTextFieldUserName.editText?.text.toString().isEmpty()) {
            binding.outlinedTextFieldUserName.error = "User name is required"
            return
        } else
            binding.outlinedTextFieldUserName.error = null

        if (binding.outlinedTextFieldPassword.editText?.text.toString().isEmpty()) {
            binding.outlinedTextFieldPassword.error = "Password is required"
            return
        } else
            binding.outlinedTextFieldPassword.error = null

        updatePassword(passwordModel)
    }

    private fun updatePassword(passwordModel: PasswordModel) {
        viewModel.updatePassword(
            passwordModel.copy(
                website = binding.outlinedTextFieldSite.editText?.text.toString(),
                username = binding.outlinedTextFieldUserName.editText?.text.toString(),
                password = viewModel.encryptPassword(
                    binding.outlinedTextFieldUserName.editText?.text.toString(),
                    binding.outlinedTextFieldPassword.editText?.text.toString()
                )!!
            )
        )
        findNavController().navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_delete, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                navigateToConfirmDeleteDialog(viewModel.passwordModel!!)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToConfirmDeleteDialog(passwordModel: PasswordModel) {
        val action = DetailFragmentDirections.actionDetailFragmentToConfirmDialog(passwordModel)
        findNavController().navigate(action)
    }

    override fun onPause() {
        super.onPause()
        binding.outlinedTextFieldPassword.editText?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }
}