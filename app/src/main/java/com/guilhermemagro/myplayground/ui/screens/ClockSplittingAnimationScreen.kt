package com.guilhermemagro.myplayground.ui.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Slider
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.animations.ClockAnimated

@Composable
fun ClockSplittingAnimationScreen() {

    val infiniteTransition = rememberInfiniteTransition()
    val duration: Int = 6000

    // Creates a child animation of float type as a part of the [InfiniteTransition].
    val animationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    var sliderValue by remember { mutableStateOf(0f) }

    var sliderEnabled by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            ClockAnimated(
                modifier = Modifier.size(300.dp),
                animationAngle = if (sliderEnabled) sliderValue else animationAngle
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Control animation with slider")
            Switch(
                checked = sliderEnabled,
                onCheckedChange = { sliderEnabled = it }
            )
        }
        Slider(
            modifier = Modifier.padding(16.dp),
            enabled = sliderEnabled,
            value = sliderValue,
            valueRange = 0f.rangeTo(720f),
            onValueChange = {
                sliderValue = it
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClockSplittingAnimationScreenPreview() {
    ClockSplittingAnimationScreen()
}
