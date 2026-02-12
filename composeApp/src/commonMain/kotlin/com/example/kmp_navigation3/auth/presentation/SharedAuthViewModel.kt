package com.example.kmp_navigation3.auth.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedAuthViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun increaseCounter() = _counter.value++

    init {
        println("SharedAuthViewModel Init")
    }

    override fun onCleared() {
        super.onCleared()
        println("SharedAuthViewModel onCleared")
    }
}