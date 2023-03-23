package com.guilhermemagro.myplayground.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.guilhermemagro.myplayground.model.CircularNavBarItem

@Composable
fun CircularNavBar(
    modifier: Modifier = Modifier,
    allTabs: List<CircularNavBarItem>,
    activeTab: CircularNavBarItem,
) {
    Row {
        allTabs.forEach {
            Icon(
                imageVector = it.iconImageVector,
                contentDescription = null,
            )
        }
    }
}
