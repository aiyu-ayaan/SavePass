package com.ab.savepass.ui.fragments.about.delete_all

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAllDialogFragment : DialogFragment() {

    private val viewModel: DeleteAllViewModel by viewModels()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm Deletion")
            .setMessage("Do you really want to delete all saved passwords?")
            .setPositiveButton("Yes") { _, _ ->
                viewModel.deleteAll()
            }
            .setNegativeButton("No", null)
            .create()
}