package com.example.kmp_navigation3.auth.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    sharedAuthViewModel: SharedAuthViewModel,
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val loginCounter by loginViewModel.counter.collectAsStateWithLifecycle()
    val sharedCounter by sharedAuthViewModel.counter.collectAsStateWithLifecycle()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = sharedAuthViewModel::increaseCounter
        ) {
            Text("Shared counter: $sharedCounter")
        }
        Button(
            onClick = loginViewModel::increaseCounter
        ) {
            Text("Local counter: $loginCounter")
        }
        Button(
            onClick = onLogin
        ) {
            Text("Login user")
        }
        Button(
            onClick = onRegister
        ) {
            Text("Register user")
        }
    }


}