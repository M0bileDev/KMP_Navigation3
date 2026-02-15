package com.example.kmp_navigation3.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.kmp_navigation3.navigation.BottomNavItem.Companion.topLevelDestinations
import com.example.kmp_navigation3.screens.DetailScreen
import com.example.kmp_navigation3.screens.ListScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val navigationState = rememberNavigationState(
        startRoute = Route.ListScreen,
        topLevelRoutes = topLevelDestinations.keys
    )

    Scaffold(
        bottomBar = {
            TodoNavigationBar(
                selectKey = Route.ListScreen,
                onKeyChange = {
                    // TODO: provide implementation
                }
            )
        }
    ) { innerPadding ->

        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = {},
            entries = navigationState.toEntries(
                entryProvider = entryProvider {
                    entry<Route.ListScreen> {
                        ListScreen(
                            onClick = { todo ->
                                navBackStack.add(Route.DetailScreen(todo))
                            }
                        )
                    }
                    entry<Route.DetailScreen> {
                        DetailScreen(it.todo)
                    }
                    entry<Route.TodoFavorites> {
                        ListScreen(
                            onClick = { todo ->
                                navBackStack.add(Route.DetailScreen(todo))
                            }
                        )
                    }
                    entry<Route.Settings> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("Settings")
                        }
                    }
                }
            )
        )
    }
}