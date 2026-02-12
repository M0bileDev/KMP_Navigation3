package com.example.kmp_navigation3.todo

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.kmp_navigation3.navigation.Route
import com.example.kmp_navigation3.todo.presentation.DetailScreen
import com.example.kmp_navigation3.todo.presentation.ListScreen
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun TodoNavigation(
    modifier: Modifier = Modifier
) {

    val todoBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            //defines how routes should be serialized
            serializersModule = SerializersModule {
                //set up polymorphic serializer, to provide proper serialization mechanism
                //navigate between features like auth and to/do
                polymorphic(NavKey::class) {
                    subclass(Route.Todo.ListScreen::class, Route.Todo.ListScreen.serializer())
                    subclass(Route.Todo.DetailScreen::class, Route.Todo.DetailScreen.serializer())
                }
            }
        },
        //first screen
        Route.Todo.ListScreen
    )

    //scoped to the nearest backstack entry -> root navigation
    NavDisplay(
        modifier = modifier,
        backStack = todoBackStack,
        entryDecorators = listOf(
            //
            rememberSaveableStateHolderNavEntryDecorator(),
            //viewmodels will be scoped properly
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Todo.ListScreen> {
                ListScreen(onClick = {
                    todoBackStack.add(Route.Todo.DetailScreen(it))
                })
            }
            entry<Route.Todo.DetailScreen> {
                DetailScreen(it.todo)
            }
        }
    )
}