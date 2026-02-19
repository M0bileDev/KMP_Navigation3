package com.example.kmp_navigation3.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.kmp_navigation3.navigation.BottomNavItem.Companion.topLevelDestinations
import com.example.kmp_navigation3.navigation.scenes.ListDetailScene
import com.example.kmp_navigation3.navigation.scenes.rememberListDetailSceneStrategy
import com.example.kmp_navigation3.todo.presentation.ChangeSettingsScreen
import com.example.kmp_navigation3.todo.presentation.DetailScreen
import com.example.kmp_navigation3.todo.presentation.ListScreen
import com.example.kmp_navigation3.todo.presentation.SettingsScreen

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val navigationState = rememberNavigationState(
        startRoute = Route.ListScreen,
        topLevelRoutes = topLevelDestinations.keys
    )
    val navigator = remember {
        Navigator(navigationState)
    }

    val resultStore = rememberResultStore()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            TodoNavigationBar(
                selectKey = navigationState.topLevelRoute,
                onKeyChange = navigator::navigate
            )
        }
    ) { innerPadding ->

        NavDisplay(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            onBack = navigator::goBack,
            transitionSpec = {
                slideInHorizontally { it } + fadeIn() togetherWith slideOutHorizontally { -it } + fadeOut()
            },
            popTransitionSpec = {
                slideInHorizontally { -it } + fadeIn() togetherWith slideOutHorizontally { it } + fadeOut()
            },
            sceneStrategy = rememberListDetailSceneStrategy(),
            entries = navigationState.toEntries(
                entryProvider = entryProvider {
                    entry<Route.ListScreen>(
                        metadata = ListDetailScene.listPane()
                    ) {
                        ListScreen(
                            onClick = { todo ->
                                navigator.navigate(Route.DetailScreen(todo))
                            }
                        )
                    }
                    entry<Route.DetailScreen>(
                        metadata = ListDetailScene.detailPane()
                    ) {
                        DetailScreen(it.todo)
                    }
                    entry<Route.TodoFavorites>(
                        metadata = ListDetailScene.listPane()
                    ) {
                        ListScreen(
                            onClick = { todo ->
                                navigator.navigate(Route.DetailScreen(todo))
                            }
                        )
                    }
                    entry<Route.Settings> {
                        SettingsScreen(resultStore, onChangeSettingsClick = {
                            navigator.navigate(Route.ChangeSettings)
                        })
                    }
                    entry<Route.ChangeSettings> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            ChangeSettingsScreen(resultStore, onSave = {
                                navigator.goBack()
                            })
                        }
                    }
                }
            )
        )
    }
}