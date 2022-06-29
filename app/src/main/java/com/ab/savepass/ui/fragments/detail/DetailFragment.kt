package com.ab.savepass.ui.fragments.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ab.core.room.PasswordModel
import com.ab.savepass.R
import com.ab.savepass.databinding.FragmentDetailBinding
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val binding: FragmentDetailBinding by viewBinding()
    private val viewModel by viewModels<DetailViewModel>()


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
        }
        setHasOptionsMenu(true)
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
        passwordModel.apply {
            website = binding.outlinedTextFieldSite.editText?.text.toString()
            username = binding.outlinedTextFieldUserName.editText?.text.toString()
            password = viewModel.encryptPassword(
                binding.outlinedTextFieldUserName.editText?.text.toString(),
                binding.outlinedTextFieldPassword.editText?.text.toString()
            )!!
        }
        viewModel.updatePassword(passwordModel)
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
}