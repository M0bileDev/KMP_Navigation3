package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.kmp_navigation3.screens.DetailScreen
import com.example.kmp_navigation3.screens.ListScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val navBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            //defines how routes should be serialized
            serializersModule = SerializersModule {
                //set up polymorphic serializer, to provide proper serialization mechanism
                polymorphic(NavKey::class) {
                    subclass(Route.ListScreen::class, Route.ListScreen.serializer())
                    subclass(Route.DetailScreen::class, Route.DetailScreen.serializer())
                }
            }
        }, Route.ListScreen
    )

    NavDisplay(
        modifier = modifier,
        backStack = navBackStack,
        entryProvider = { key ->
            when (key) {
                is Route.ListScreen -> {
                    NavEntry(key) {
                        ListScreen(
                            onClick = { todo ->
                                navBackStack.add(Route.DetailScreen(todo))
                            }
                        )
                    }
                }

                is Route.DetailScreen -> {
                    NavEntry(key) {
                        DetailScreen(key.todo)
                    }
                }

                else -> error("Unknown NavKey; $key")
            }
        }
    )
}