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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

@Composable
fun ClockAnimated(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Black,
    animationAngle: Float = 0f
) {
    fun calculateClockHandLength(stepHeight: Float, currentHour: Int): Float {
        // Height decreases first 360 deg, then increases again
        val stepsNumber = if (currentHour < 12) {
            11 - currentHour
        } else {
            currentHour - 12
        }
        return stepHeight * stepsNumber
    }

    fun calculateAssembleDistance(stepHeight: Float, currentHour: Int): Float =
        stepHeight * (23 - currentHour)

    val dotsPositions = remember(animationAngle) {
        List(12) { currentDot ->
            val easing = LinearOutSlowInEasing
            val degreeLimit = 45f
            // currentDot is an index of the dot
            val startAngle = currentDot * 30f
            val currentDeg = (animationAngle - startAngle).coerceIn(0f, degreeLimit)
            // Progression from 0 to 1
            val progression = currentDeg/degreeLimit
            easing.transform(progression)
        }
    }

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

    LaunchedEffect(animationAngle) {
        // Add hour calculation inside of a launchEffect
        val newCurrentHour = animationAngle.toInt() / 30

        if (newCurrentHour != currentHour) {
            currentHour = newCurrentHour
            currentHourChannel.trySend(currentHour)
        }
    }

    val hours = remember { List(12) { it } }

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
        .padding(8.dp)
        .background(backgroundColor)
        // Set strokeWidth based on the size of the viewport
        .onGloballyPositioned {
            strokeWidth = (it.size.width / 24).toFloat()
        }
        .drawBehind {
            val halfWidth = size.width / 2
            val halfHeight = size.height / 2
            val halfStroke = strokeWidth / 2
            val stepHeight = (halfHeight - strokeWidth) / 12

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
                    cap = StrokeCap.Round,
                    blendMode = BlendMode.DstOut,
                )

                // Drawing a clock hand piece
                if (assembleValue != -1f) {
                    val positionY = halfStroke +
                            calculateAssembleDistance(stepHeight, currentHour) * assembleValue

                    val start = Offset(halfWidth, positionY)
                    val end = Offset(halfWidth, positionY + halfStroke)
                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round,
                        blendMode = BlendMode.DstOut,
                    )
                }
            }

            hours.forEach { hour ->
                if (!dotsVisibility[hour]) return@forEach
                val degree = hour * 30f
                rotate(degree) {
                    // Based on the hour value, the travel distance will be longer
                    val positionY = halfStroke +
                            stepHeight * hour * (1 - dotsPositions[hour])

                    val start = Offset(x = halfWidth, y = positionY)
                    val end = Offset(x = halfWidth, y = positionY + halfStroke)

                    drawLine(
                        color = Color.White,
                        start = start,
                        end = end,
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Round,
                        blendMode = BlendMode.DstOut,
                    )
                }
            }

            // Rotation of the gradient
            rotate(degrees = animationAngle / 2, pivot = center) {
                drawCircle(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Red, Color.Yellow, Color.Green, Color.Blue, Color.Magenta
                        )
                    ),
                    blendMode = BlendMode.DstAtop,
                )
            }
        }
    )
}
