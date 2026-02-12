package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.kmp_navigation3.auth.AuthNavigation
import com.example.kmp_navigation3.todo.TodoNavigation
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {

    val rootBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            //defines how routes should be serialized
            serializersModule = SerializersModule {
                //set up polymorphic serializer, to provide proper serialization mechanism
                //navigate between features like auth and to/do
                polymorphic(NavKey::class) {
                    subclass(Route.Auth::class, Route.Auth.serializer())
                    subclass(Route.Todo::class, Route.Todo.serializer())
                }
            }
        },
        //place condition for nested navigation (e.g. user is already logged -> to/do, user not logged yet -> auth
        Route.Auth
    )

    NavDisplay(
        modifier = modifier,
        backStack = rootBackStack,
        entryDecorators = listOf(
            //
            rememberSaveableStateHolderNavEntryDecorator(),
            //viewmodels will be scoped properly
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Auth> {
                AuthNavigation(
                    onLogin = {
                        rootBackStack.apply {
                            remove(Route.Auth)
                            add(Route.Todo)
                        }
                    }
                )
            }
            entry<Route.Todo> {
                TodoNavigation()
            }
        }
    )
}