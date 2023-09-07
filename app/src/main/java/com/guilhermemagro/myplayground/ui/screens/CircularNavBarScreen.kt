package com.guilhermemagro.myplayground.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.ViewModule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.guilhermemagro.myplayground.model.CircularNavBarItem
import com.guilhermemagro.myplayground.ui.components.CircularNavBar
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.components.circularnavbar.view.CustomTabBar

@Composable
fun CircularNavBarScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CustomTabBar(activeTab = Tab.PLAY)
        
        
//        CircularNavBar(
//            allTabs = listOf(
//                CircularNavBarItem(
//                    title = "Tab 1",
//                    iconImageVector = Icons.Filled.SportsEsports
//                ),
//                CircularNavBarItem(
//                    title = "Tab 2",
//                    iconImageVector = Icons.Filled.RocketLaunch
//                ),
//                CircularNavBarItem(
//                    title = "Tab 3",
//                    iconImageVector = Icons.Filled.ShoppingBag
//                ),
//                CircularNavBarItem(
//                    title = "Tab 4",
//                    iconImageVector = Icons.Filled.ViewModule
//                ),
//                CircularNavBarItem(
//                    title = "Tab 5",
//                    iconImageVector = Icons.Filled.Search
//                ),
//            ),
//            activeTab = CircularNavBarItem(
//                title = "Selected Tab",
//                iconImageVector = Icons.Filled.ShoppingBag
//            )
//        )
    }
}
