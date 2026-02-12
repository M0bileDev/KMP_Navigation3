package com.example.kmp_navigation3.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.savedstate.serialization.SavedStateConfiguration
import com.example.kmp_navigation3.auth.presentation.LoginScreen
import com.example.kmp_navigation3.auth.presentation.LoginViewModel
import com.example.kmp_navigation3.auth.presentation.RegisterScreen
import com.example.kmp_navigation3.auth.presentation.RegisterViewModel
import com.example.kmp_navigation3.auth.presentation.SharedAuthViewModel
import com.example.kmp_navigation3.navigation.Route
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

@Composable
fun AuthNavigation(
    onLogin: () -> Unit,
    modifier: Modifier = Modifier
) {

    val authBackStack = rememberNavBackStack(
        configuration = SavedStateConfiguration {
            //defines how routes should be serialized
            serializersModule = SerializersModule {
                //set up polymorphic serializer, to provide proper serialization mechanism
                //navigate between features like auth and to/do
                polymorphic(NavKey::class) {
                    subclass(Route.Auth.Login::class, Route.Auth.Login.serializer())
                    subclass(Route.Auth.Register::class, Route.Auth.Register.serializer())
                }
            }
        },
        //first screen
        Route.Auth.Login
    )

    //scoped to the nearest backstack entry -> root navigation
    val sharedAuthViewModel = viewModel { SharedAuthViewModel() }
    NavDisplay(
        modifier = modifier,
        backStack = authBackStack,
        entryDecorators = listOf(
            //
            rememberSaveableStateHolderNavEntryDecorator(),
            //viewmodels will be scoped properly
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Route.Auth.Login> {
                LoginScreen(
                    loginViewModel = viewModel { LoginViewModel() },
                    sharedAuthViewModel = sharedAuthViewModel,
                    onLogin = onLogin,
                    onRegister = {
                        authBackStack.add(Route.Auth.Register)
                    },
                )
            }
            entry<Route.Auth.Register> {
                RegisterScreen(
                    registerViewModel = viewModel { RegisterViewModel() },
                    sharedAuthViewModel = sharedAuthViewModel,
                )
            }
        }
    )
}