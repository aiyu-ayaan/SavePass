package com.ab.savepass.ui.fragments.detail.cofirm_dialog

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ab.core.repositories.PasswordRepository
import com.ab.core.room.PasswordModel
import com.ab.core.scope.SavePassScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmDialogViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PasswordRepository,
    @SavePassScope private val coroutineScope: CoroutineScope
) : ViewModel() {
    val passwordModel = state.get<PasswordModel>("passwordModel")
    fun deletePassword(passwordModel: PasswordModel) {
        coroutineScope.launch {
            repository.deletePassword(passwordModel)
        }
    }
}