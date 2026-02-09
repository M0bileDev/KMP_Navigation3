package com.example.kmp_navigation3.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ListScreenViewModel : ViewModel() {

    private val _todos = MutableStateFlow(
        value = (1..100).map { item -> "Todo $item" }
    )
    val todos get() = _todos.asStateFlow()
}