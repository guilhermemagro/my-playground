package com.guilhermemagro.myplayground.navigation

import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.guilhermemagro.myplayground.ui.screens.CircularNavBarScreen
import com.guilhermemagro.myplayground.ui.screens.ClockSplittingAnimationScreen
import com.guilhermemagro.myplayground.ui.screens.HomeScreen
import com.guilhermemagro.myplayground.viewmodels.CircularNavBarViewModel

@Composable
fun AppNavigation() {
    val scaffoldState = rememberScaffoldState()
    val appCoroutineScope = rememberCoroutineScope()
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(onNavigationClick = { route ->
                navController.navigate(route)
            })
        }
        composable(route = Screen.CircularNavBar.route) {
            val circularNavBarViewModel: CircularNavBarViewModel = hiltViewModel()
            CircularNavBarScreen()
        }
        composable(route = Screen.ClockSplittingAnimation.route) {
            ClockSplittingAnimationScreen()
        }
    }
}
