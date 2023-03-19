package com.guilhermemagro.myplayground.navigation

sealed class Screen(val title: String, val route: String) {
    object Home: Screen(title = "Home", route = "home")
    object CircularNavBar: Screen(title = "CircularNavBar", route = "circularnavbar")

    companion object {
        fun allComponentScreens(): List<Screen> = listOf(
            CircularNavBar
        )
    }
}
