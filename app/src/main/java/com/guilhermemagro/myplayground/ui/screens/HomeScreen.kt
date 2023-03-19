package com.guilhermemagro.myplayground.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.guilhermemagro.myplayground.navigation.Screen
import com.guilhermemagro.myplayground.ui.components.ListItemView

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState,
    ) {
        items(Screen.allComponentScreens()) {
            ListItemView(title = it.title)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}
