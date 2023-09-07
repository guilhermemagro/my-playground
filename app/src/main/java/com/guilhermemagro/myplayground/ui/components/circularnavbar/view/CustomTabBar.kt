package com.guilhermemagro.myplayground.ui.components.circularnavbar.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab

@Composable
fun CustomTabBar(
    activeTab: Tab,
    modifier: Modifier = Modifier,
) {
    Row {
        Tab.values().forEach {
            Image(imageVector = it.image, contentDescription = null)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTabBarPreview() {
    CustomTabBar(activeTab = Tab.LIBRARY)
}