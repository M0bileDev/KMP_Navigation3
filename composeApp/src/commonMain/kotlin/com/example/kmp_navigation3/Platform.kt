package com.example.kmp_navigation3

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform