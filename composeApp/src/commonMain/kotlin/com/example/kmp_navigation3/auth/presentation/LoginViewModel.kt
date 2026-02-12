package com.example.kmp_navigation3.auth.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _counter = MutableStateFlow(0)
    val counter = _counter.asStateFlow()

    fun increaseCounter() = _counter.value++

    init {
        println("LoginViewModel Init")
    }

    override fun onCleared() {
        super.onCleared()
        println("LoginViewModel onCleared")
    }
}