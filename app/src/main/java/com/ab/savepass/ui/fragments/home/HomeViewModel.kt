package com.ab.savepass.ui.fragments.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ab.core.repositories.PasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val state: SavedStateHandle,
    private val repository: PasswordRepository
) : ViewModel(){
    fun getPasswords() = repository.getAllPasswords()
}