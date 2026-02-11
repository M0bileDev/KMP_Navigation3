package com.example.kmp_navigation3.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DetailScreenViewModel(
    todo: String
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState(todo))
    val detailState get() = _detailState.asStateFlow()
}

data class DetailState(
    val todo: String
)