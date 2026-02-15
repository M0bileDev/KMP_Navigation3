package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
import androidx.savedstate.serialization.SavedStateConfiguration
import kotlinx.serialization.PolymorphicSerializer
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic

class NavigationState(
    // first visible destination
    val startRoute: NavKey,
    // destinations visible from BottomView
    topLevelRoute: MutableState<NavKey>,
    // feature destination <-> list of visible destination per feature
    val backStacks: Map<NavKey, NavBackStack<NavKey>>
) {

    var topLevelRoute by topLevelRoute
        private set

    //backstack currently in use
    val stacksInUse: List<NavKey>
        //  do not keep any additional backstack
        // if the target destination is home destination (start destination)
        //  e.g.
        //              BottomBar
        //      (home) - [feature1] - (feature2)        feature1 selected (press navigate back)
        //      [home] - (feature1) - (feature2)        home is selected (press navigate back -> exit the app)
        get() = if (topLevelRoute == startRoute) {
            listOf(startRoute)
        } else {
            // keep additional backstack if the
            // destination is part of the feature that is not part of the start destination
            //  e.g.
            //              BottomBar
            //      [home] - (feature1) - (feature2)        home is selected (navigate to feature1)
            //      [home] - (feature1) - (feature2)        feature1 selected -> stack: (home/feature1)
            listOf(
                startRoute,
                topLevelRoute
            )
        }
}

@Composable
fun rememberNavigationState(
    startRoute: NavKey,
    topLevelRoutes: Set<NavKey>
): NavigationState {
    val topLevelRoute = rememberSerializable(
        startRoute,
        topLevelRoutes,
        configuration = configuration,
        serializer = MutableStateSerializer(PolymorphicSerializer(NavKey::class))
    ) {
        mutableStateOf(startRoute)
    }

    val backStacks = topLevelRoutes.associateWith { key ->
        rememberNavBackStack(
            configuration = configuration,
            //first destination per feature
            key
        )
    }

    return remember(startRoute, topLevelRoutes) {
        NavigationState(
            startRoute = startRoute,
            topLevelRoute = topLevelRoute,
            backStacks = backStacks
        )
    }
}

val configuration = SavedStateConfiguration {
    //defines how routes should be serialized
    serializersModule = SerializersModule {
        //set up polymorphic serializer, to provide proper serialization mechanism
        polymorphic(NavKey::class) {
            subclass(Route.ListScreen::class, Route.ListScreen.serializer())
            subclass(Route.DetailScreen::class, Route.DetailScreen.serializer())
            subclass(Route.TodoFavorites::class, Route.TodoFavorites.serializer())
            subclass(Route.Settings::class, Route.Settings.serializer())
        }
    }
}