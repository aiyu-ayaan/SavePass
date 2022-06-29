package com.ab.savepass.ui.fragments.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.core.repositories.AESCryptRepository
import com.ab.core.repositories.PasswordRepository
import com.ab.core.room.PasswordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PasswordRepository,
    private val aesCryptRepository: AESCryptRepository
) : ViewModel() {
    val passwordModel = state.get<PasswordModel>("passwordModel")

    fun updatePassword(passwordModel: PasswordModel) = viewModelScope.launch {
        repository.updatePassword(passwordModel)
    }

    fun deletePassword(passwordModel: PasswordModel) = viewModelScope.launch {
        repository.deletePassword(passwordModel)
    }

    fun getPassword(userName: String, hashedPassword: String) =
        aesCryptRepository.decrypt(userName, hashedPassword)

    fun encryptPassword(userName: String, password: String) =
        aesCryptRepository.encrypt(userName, password)

}