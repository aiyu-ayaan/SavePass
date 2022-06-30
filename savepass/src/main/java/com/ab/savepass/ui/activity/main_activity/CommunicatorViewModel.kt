package com.ab.savepass.ui.activity.main_activity

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class CommunicatorViewModel @Inject constructor(
    private val state: SavedStateHandle
) : ViewModel() {


    var isAuthenticated = MutableStateFlow(true)
}