package com.example.kmp_navigation3.todo.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.kmp_navigation3.navigation.ResultStore
import com.example.kmp_navigation3.navigation.ResultStore.Companion.TEXT_FIELD_SETTING_KEY


@Composable
fun SettingsScreen(
    resultStore: ResultStore,
    onChangeSettingsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val result = resultStore.getResult<String>(TEXT_FIELD_SETTING_KEY)
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button(
            onClick = onChangeSettingsClick
        ) {
            Text("Current setting: ${result ?: "Default"}")
        }
    }
}