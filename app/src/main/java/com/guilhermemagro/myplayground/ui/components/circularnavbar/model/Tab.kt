package com.guilhermemagro.myplayground.ui.components.circularnavbar.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Enum Tab Cases
 */
enum class Tab(val title: String, val icon: ImageVector) {
    ZERO_PLAY("Play", Icons.Filled.SportsEsports),
    ONE_EXPLORE("Explore", Icons.Filled.RocketLaunch),
    TWO_STORE("PS Store", Icons.Filled.ShoppingBag),
    THREE_LIBRARY("Game Library", Icons.Filled.ViewModule),
    FOUR_SEARCH("Search", Icons.Filled.Search)
}
