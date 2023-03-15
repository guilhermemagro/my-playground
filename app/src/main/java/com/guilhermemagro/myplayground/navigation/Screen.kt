package com.guilhermemagro.myplayground.navigation

sealed class Screen(val title: String, val route: String) {
    object HomeScreen: Screen("Home", "home")
    object CircularNavBarScreen: Screen("CircularNavBar", "circularnavbar")
}
