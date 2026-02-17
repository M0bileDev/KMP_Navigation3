package com.example.kmp_navigation3.navigation.scenes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene

class ListDetailScene<T : Any>(
    //destination of the list screen
    val list: NavEntry<T>,
    val detail: NavEntry<T>,
    override val key: Any,
    override val previousEntries: List<NavEntry<T>>
) : Scene<T> {

    //list of views belongs to the scene
    override val entries: List<NavEntry<T>>
        get() = listOf(list, detail)

    override val content: @Composable (() -> Unit) = {
        //arrange, how the list and detail will look like in compose
        Row(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.weight(0.4f)
            ) {
                //view of the list
                list.Content()
            }
            Column(
                modifier = Modifier.weight(0.6f)
            ) {
                //view of the list
                detail.Content()
            }
        }
    }

    companion object{
        const val LIST_KEY = "ListDetailScene-List"
        const val DETAIL_KEY = "ListDetailScene-Detail"

        //helper functions reference to some kind of entry
        fun listPane() = mapOf(LIST_KEY to true) //that nav entry is considered as list screen
        fun detailPane() = mapOf(DETAIL_KEY to true)
    }
}