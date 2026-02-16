package com.example.kmp_navigation3.navigation.scenes

import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowSizeClass.Companion.WIDTH_DP_MEDIUM_LOWER_BOUND

class ListDetailSceneStrategy<T : Any>(
    val windowSizeClass: WindowSizeClass,
) : SceneStrategy<T> {

    //return null causes fallback to the default scene, top most nav key on the backstack
    override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? {
        //must be at least medium size screen width
        if (!windowSizeClass.isWidthAtLeastBreakpoint(WIDTH_DP_MEDIUM_LOWER_BOUND)) return null

        // TODO: provide impl
    }
}