package com.ab.savepass.ui.fragments.add_password

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.ab.core.repositories.AESCryptRepository
import com.ab.core.repositories.PasswordRepository
import com.ab.core.room.PasswordModel
import com.ab.core.scope.SavePassScope
import com.ab.savepass.R
import com.ab.savepass.databinding.DialogAddPasswordBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddPasswordDialogFragment : DialogFragment() {
    @Inject
    lateinit var repository: PasswordRepository

    @SavePassScope
    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var aesCryptRepository: AESCryptRepository

    private lateinit var binding: DialogAddPasswordBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddPasswordBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(
            requireContext()
        )
            .setTitle("Add Password")
            .setView(binding.root)
            .setPositiveButton(
                resources.getString(R.string.add), null
            ).setNegativeButton(
                resources.getString(R.string.cancel), null
            )
            .create()
        dialog.setOnShowListener {
            val button = dialog.getButton(Dialog.BUTTON_POSITIVE)
            button.setOnClickListener {
                checkInput()
            }
        }
        return dialog
    }

    private fun checkInput() = binding.apply {
        if (outlinedTextFieldName.editText?.text.toString().isBlank()) {
            outlinedTextFieldName.error = "Name is required"
            return@apply
        } else
            outlinedTextFieldName.error = null
        if (outlinedTextFieldPawsword.editText?.text.toString().isBlank()) {
            outlinedTextFieldPawsword.error = "Password is required"
            return@apply
        } else
            outlinedTextFieldPawsword.error = null

        if (outlinedTextFieldWebsite.editText?.text.toString().isBlank()) {
            outlinedTextFieldWebsite.error = "Url is required"
            return@apply
        } else
            outlinedTextFieldWebsite.error = null

        hashPassword(
            outlinedTextFieldName.editText?.text.toString(),
            outlinedTextFieldPawsword.editText?.text.toString(),
            outlinedTextFieldWebsite.editText?.text.toString()
        )

    }

    private fun hashPassword(userName: String, password: String, url: String) {
        scope.launch {
            val hashPassword = aesCryptRepository.encrypt(userName, password)
            hashPassword?.let {
                val passwordModel = PasswordModel(
                    userName,
                    it,
                    url
                )
                repository.insertPassword(passwordModel)
                Log.d("AddUpdatePasswordDialogFragment", "Password added")
                dismiss()
            }

            dismiss()
        }
    }


}