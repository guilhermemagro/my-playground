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
 * Raw Value: Asset Image Name
 */
enum class Tab(val title: String, val image: ImageVector) {
    PLAY("Play", Icons.Filled.SportsEsports),
    EXPLORE("Explore", Icons.Filled.RocketLaunch),
    STORE("PS Store", Icons.Filled.ShoppingBag),
    LIBRARY("Game Library", Icons.Filled.ViewModule),
    SEARCH("Search", Icons.Filled.Search)
}
