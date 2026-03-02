package com.openclaw.android.ui.navigation

sealed class Screen(val route: String) {
    object Chat : Screen("chat")
    object Voice : Screen("voice")
    object Canvas : Screen("canvas")
    object Settings : Screen("settings")
    object Connect : Screen("connect")
}
