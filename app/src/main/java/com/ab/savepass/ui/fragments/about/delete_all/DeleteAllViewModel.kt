package com.ab.savepass.ui.fragments.about.delete_all

import androidx.lifecycle.ViewModel
import com.ab.core.repositories.PasswordRepository
import com.ab.core.scope.SavePassScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAllViewModel @Inject
constructor(
    private val repository: PasswordRepository,
    @SavePassScope
    private val scope: CoroutineScope
) :ViewModel(){
    fun deleteAll() = scope.launch {
        repository.deleteAllPassword()
    }
}