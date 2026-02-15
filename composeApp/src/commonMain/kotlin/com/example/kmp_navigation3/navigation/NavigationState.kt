package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSerializable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberDecoratedNavEntries
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
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

@Composable
fun NavigationState.toEntries(
    entryProvider: (NavKey) -> NavEntry<NavKey>
):
//      1. return list of compose states,
//      2. state could change and compose will be notified
        SnapshotStateList<NavEntry<NavKey>> {
    //iterate over back stacks and provide entry decorators
    val decoratedEntries = backStacks.mapValues { (_, stack) ->

        val decorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator<NavKey>(),
            //viewmodels will be scoped properly
            rememberViewModelStoreNavEntryDecorator()
        )

        //list of nav entry -> each entry is one destination
        rememberDecoratedNavEntries(
            backStack = stack,
            entryDecorators = decorators,
            //defines which screen can be visited
            entryProvider = entryProvider
        )
    }

    return stacksInUse
        //top level destinations -> only start destination or start destination + feature destination
        .flatMap {
            //access decorated entry by key
            decoratedEntries[it] ?: emptyList()
        }
        .toMutableStateList()
}