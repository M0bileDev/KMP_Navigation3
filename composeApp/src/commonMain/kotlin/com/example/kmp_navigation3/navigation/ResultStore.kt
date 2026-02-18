package com.example.kmp_navigation3.navigation

import androidx.compose.runtime.saveable.Saver

class ResultStore {
    private val results = mutableMapOf<Any, Any?>()

    @Suppress("UNCHECKED_CAST")
    fun <T> getResult(key: Any): T? = results[key] as? T

    fun <T> setResult(key: Any, value: T) {
        results[key] = value
    }

    fun removeResult(key: Any) {
        results.remove(key)
    }

    companion object {
        //saver -> how object should be serialized and deserialized
        //eg. in compose rememberSaveable
        val Saver = Saver<ResultStore, Map<Any, Any?>>(
            //map is already serializable
            save = { it.results.toMap() },
            restore = { restoreMap ->
                ResultStore().apply {
                    results.putAll(restoreMap)
                }
            }
        )

    }
}