package com.example.kmp_navigation3.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TAG = "DetailScreenViewModel"

class DetailScreenViewModel(
    todo: String
) : ViewModel() {

    private val _detailState = MutableStateFlow(DetailState(todo))
    val detailState get() = _detailState.asStateFlow()
    
    init {
        println("$TAG Initialize view model: $this -> todo value: $todo")
    }

    override fun onCleared() {
        super.onCleared()
        println("$TAG Clear view model -> todo value: $this")

    }
}

data class DetailState(
    val todo: String
)