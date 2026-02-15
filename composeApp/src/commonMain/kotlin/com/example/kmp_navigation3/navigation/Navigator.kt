package com.example.kmp_navigation3.navigation

import androidx.navigation3.runtime.NavKey

class Navigator (
    val navigationState: NavigationState
){
    fun navigate(route: NavKey){
        //check if route is one of the top level routes
        if(route in navigationState.backStacks.keys){
            //swap to different top level route
            navigationState.topLevelRoute = route
        }else{
            //add destination to current top level route back stack
            navigationState.backStacks[navigationState.topLevelRoute]?.add(route)
        }
    }

    fun goBack(){
        val currentStack = navigationState.backStacks[navigationState.topLevelRoute] ?:
        error("back stack for ${navigationState.topLevelRoute} does not exist.")

        val currentRoute = currentStack.last()
        //if route is one of the top level routes, navigate back
        // swap top level destination to start destination
        if(currentRoute == navigationState.topLevelRoute){
            navigationState.topLevelRoute = navigationState.startRoute
        }else{
            // drop last route
            currentStack.removeLastOrNull()
        }
    }
}