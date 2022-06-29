package com.ab.savepass.ui.fragments.detail.cofirm_dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConfirmDialog : DialogFragment() {

    private val viewModel: ConfirmDialogViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete password?")
            .setMessage("Are you sure you want to delete this password?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.passwordModel?.let {
                    viewModel.deletePassword(it)
                    navigateToHome()
                }
            }.setNegativeButton("No", null)
            .create()

        return dialog
    }

    private fun navigateToHome() {
        findNavController().navigate(
            ConfirmDialogDirections.actionConfirmDialogToHomeFragment()
        )
    }
}