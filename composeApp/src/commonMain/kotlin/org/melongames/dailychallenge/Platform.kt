package org.melongames.dailychallenge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform