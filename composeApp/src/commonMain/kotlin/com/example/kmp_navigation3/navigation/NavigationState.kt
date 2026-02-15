package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

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