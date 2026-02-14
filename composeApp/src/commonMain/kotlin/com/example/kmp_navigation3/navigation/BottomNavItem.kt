package com.example.kmp_navigation3.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,
    val icon: ImageVector
) {
    companion object {
        val topLevelDestinations = mapOf(
            Route.ListScreen to BottomNavItem(label = "Todos", icon = Icons.Default.Checklist),
            Route.TodoFavorites to BottomNavItem(
                label = "Favorites",
                icon = Icons.Default.Favorite
            ),
            Route.Settings to BottomNavItem(label = "Settings", icon = Icons.Default.Settings),
        )
    }
}

