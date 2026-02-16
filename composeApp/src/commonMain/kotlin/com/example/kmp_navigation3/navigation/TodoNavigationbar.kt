package com.example.kmp_navigation3.navigation

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation3.runtime.NavKey

@Composable
fun TodoNavigationBar(
    selectKey: NavKey,
    onKeyChange: (NavKey) -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(
        modifier = modifier
    ) {
        BottomNavItem.topLevelDestinations.forEach { (destination, item) ->
            NavigationBarItem(
                selected = selectKey == destination,
                onClick = {
                    onKeyChange(destination)
                },
                icon = {
                    Icon(imageVector = item.icon, contentDescription = item.label)
                },
                label = {
                    Text(text = item.label)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewTodoNavigationBar() {
    MaterialTheme {
        TodoNavigationBar(selectKey = Route.ListScreen, onKeyChange = {})
    }
}