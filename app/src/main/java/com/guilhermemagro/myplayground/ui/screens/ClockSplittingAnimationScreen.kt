package com.guilhermemagro.myplayground.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.animations.ClockAnimated

@Composable
fun ClockSplittingAnimationScreen() {
    Box(modifier = Modifier.size(300.dp)) {
        ClockAnimated()
    }
}

@Preview(showBackground = true)
@Composable
fun ClockSplittingAnimationScreenPreview() {
    ClockSplittingAnimationScreen()
}
