package com.guilhermemagro.myplayground.ui.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private fun calculateClockHandLength(stepHeight: Float, currentHour: Int): Float {
    // Height decreases first 360 deg, then increases again
    val stepsNumber = if (currentHour < 12) {
        11 - currentHour
    } else {
        currentHour - 12
    }
    return stepHeight * stepsNumber
}

private fun calculateAssembleDistance(stepHeight: Float, currentHour: Int): Float =
    stepHeight * (23 - currentHour)

@Composable
fun ClockAnimated(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    duration: Int = 6000,
) {
    val infiniteTransition = rememberInfiniteTransition()

    // Creates a child animation of float type as a part of the [InfiniteTransition].
    val animationAngle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 720f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Start calculation each time the animationAngle changes.
    val assembleValue = remember(animationAngle) {
        // We only need this animation for second rotation
        if (animationAngle >= 360) {
            // Reversed linear interpolation between 0..30 degrees, transformed into 0..1
            (animationAngle % 30) / 30
        } else {
            -1f
        }
    }

    var currentHour by remember { mutableStateOf(0) }

    val currentHourChannel = remember { Channel<Int>(12, BufferOverflow.DROP_OLDEST) }
    val currentHourFlow = remember(currentHourChannel) {
        currentHourChannel.receiveAsFlow()
    }

    LaunchedEffect(animationAngle) {
        // Add hour calculation inside of a launchEffect
        val newCurrentHour = animationAngle.toInt() / 30

        if (newCurrentHour != currentHour) {
            currentHour = newCurrentHour
            currentHourChannel.trySend(currentHour)
        }
    }

    val hours = remember { List(12) { it } }

    val disassembleAnimations = remember { hours.map { Animatable(1f) } }

    // Assume that duration is 1/12th of the whole duration, which equals to the length of 2 hours.
    // It can be longer or shorter if necessary
    val disassembleDuration = duration / 12

    LaunchedEffect(currentHourFlow) {
        currentHourFlow.collectLatest {
            // Launch each animation asynchronously
            launch {
                if (currentHour < 12) {
                    disassembleAnimations[currentHour].snapTo(0f)
                    // Set a tween spec with LinearOutSlowInEasing
                    disassembleAnimations[currentHour].animateTo(
                        1f,
                        tween(durationMillis = disassembleDuration, easing = LinearOutSlowInEasing)
                    )
                }
            }
        }
    }

    val dotsVisibility = remember(currentHour) {
        hours.map { hour ->
            when {
                hour > currentHour -> false
                hour > currentHour - 12 -> true
                else -> false
            }
        }
    }

    var strokeWidth by remember { mutableStateOf(0f) }

    Spacer(modifier = modifier
        .fillMaxSize()
        .background(backgroundColor)
        // Set strokeWidth based on the size of the viewport
        .onGloballyPositioned {
            strokeWidth = (it.size.width / 24).toFloat()
        }
        .drawBehind {
            val halfWidth = size.width / 2
            val halfHeight = size.height / 2
            val halfStroke = strokeWidth / 2
            val stepHeight = halfHeight / 12

            val center = Offset(x = halfWidth, y = halfHeight)
            val endOffset = Offset(
                x = halfWidth,
                y = halfHeight - calculateClockHandLength(stepHeight, currentHour)
            )
            // Rotate the line around the pivot point, which is the
            // center of the screen. Rotation goes from 0 to 720 degrees
            rotate(degrees = animationAngle, pivot = center) {
                // Drawing a clock hand itself
                drawLine(
                    color = Color.White,
                    start = center,
                    end = endOffset,
                    strokeWidth = strokeWidth,
                )

                // Drawing a clock hand piece
                if (assembleValue != -1f) {
                    val positionY = halfStroke +
                            calculateAssembleDistance(stepHeight, currentHour) * assembleValue

                    val start = Offset(halfWidth, positionY - halfStroke)
                    val end = Offset(halfWidth, positionY + halfStroke)
                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                    )
                }
            }

            hours.forEach { hour ->
                if (!dotsVisibility[hour]) return@forEach
                val degree = hour * 30f
                rotate(degree) {
                    // Based on the hour value, the travel distance will be longer
                    val positionY = halfStroke +
                            stepHeight * hour * (1 - disassembleAnimations[hour].value)

                    val start = Offset(x = halfWidth, y = positionY - halfStroke)
                    val end = Offset(x = halfWidth, y = positionY + halfStroke)

                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                    )
                }
            }
        }
    )
}
