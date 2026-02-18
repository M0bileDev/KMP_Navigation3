package com.example.kmp_navigation3.todo.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kmp_navigation3.navigation.ResultStore
import com.example.kmp_navigation3.navigation.ResultStore.Companion.TEXT_FIELD_SETTING_KEY

private const val TEXT_FIELD_SETTING = "TEXT_FIELD_SETTING"

@Composable
fun ChangeSettingsScreen(
    resultStore: ResultStore,
    onSave: () -> Unit,
    modifier: Modifier = Modifier,
) {

    var text by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.CenterVertically)
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                text = it
            }
        )
        Button(
            onClick = {
                resultStore.setResult(TEXT_FIELD_SETTING_KEY, text)
                onSave()
            }
        ) {
            Text("Save")
        }
    }
}