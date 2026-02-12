package com.example.kmp_navigation3.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {

    @Serializable
    data object Auth: Route {

        @Serializable
        data object Login : Route

        @Serializable
        data object Register : Route

    }

    @Serializable
    data object Todo: Route {

        @Serializable
        data object ListScreen : Route

        @Serializable
        data class DetailScreen(val todo: String) : Route
    }

}