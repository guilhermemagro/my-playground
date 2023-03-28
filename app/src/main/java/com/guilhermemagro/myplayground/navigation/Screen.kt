package com.guilhermemagro.myplayground.navigation

sealed class Screen(val title: String, val route: String) {
    object Home : Screen(title = "Home", route = "home")
    object CircularNavBar : Screen(title = "CircularNavBar", route = "circularnavbar")

    // https://proandroiddev.com/amazing-clock-animation-with-jetpack-compose-part-1-8d143f38a3cd
    object ClockSplittingAnimation : Screen(title = "ClockSplittingAnimation", route = "clocksplittinganimation")

    companion object {
        fun allComponentScreens(): List<Screen> = listOf(
            CircularNavBar,
            ClockSplittingAnimation
        )
    }
}
