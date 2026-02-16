package com.example.kmp_navigation3

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.kmp_navigation3.navigation.NavigationRoot

@Composable
@Preview
fun App() {
    MaterialTheme {
        NavigationRoot()
    }
}