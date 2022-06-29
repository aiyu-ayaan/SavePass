package com.ab.savepass.ui.fragments.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ab.core.repositories.PasswordRepository
import com.ab.core.room.PasswordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PasswordRepository
) : ViewModel() {
    fun getPasswords() = repository.getAllPasswords()

}