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

        //need to know which one of the screens need to be positioned as list and detail screen
        //detail (last one on the backstack) and list(element before last on the backstack)

        val detailEntry = entries
            .lastOrNull()
            ?.takeIf {
                //check if scene has a specific key (like detail)
                it.metadata.containsKey(ListDetailScene.DETAIL_KEY)
            } ?: return null

        val listEntry = entries
            .findLast {
                it.metadata.containsKey(ListDetailScene.LIST_KEY)
            } ?: return null

        return ListDetailScene(
            list = listEntry,
            detail = detailEntry,
            key = listEntry.contentKey,
            // when navigate back, where should it end up
            previousEntries = entries.dropLast(1)
        )
    }
}