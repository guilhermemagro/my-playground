package com.guilhermemagro.myplayground.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.components.circularnavbar.view.CustomTabBar
import com.guilhermemagro.myplayground.ui.theme.MyPlaygroundTheme

@Composable
fun CircularNavBarScreen() {
    Box(
        contentAlignment = Alignment.BottomCenter,
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        CustomTabBar(activeTab = Tab.ZERO_PLAY)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Composable
fun CircularNavBarScreenPreview() {
    MyPlaygroundTheme {
        CircularNavBarScreen()
    }
}