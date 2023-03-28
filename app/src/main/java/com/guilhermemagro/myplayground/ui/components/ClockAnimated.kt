package com.guilhermemagro.myplayground.ui.animations

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
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

private fun calculateClockHandLength(maxHeight: Float, currentHour: Int): Float {
    val stepHeight = maxHeight / 12
    // Height decreases first 360 deg, then increases again
    val stepsNumber = if (currentHour < 12) {
        11 - currentHour
    } else {
        currentHour - 12
    }
    return stepHeight * stepsNumber
}

@Composable
fun ClockAnimated(
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

    val currentHour by remember(animationAngle) {
        derivedStateOf { animationAngle.toInt() / 30 }
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

    Spacer(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        // Set strokeWidth based on the size of the viewport
        .onGloballyPositioned {
            strokeWidth = (it.size.width / 24).toFloat()
        }
        .drawBehind {
            val halfWidth = size.width / 2
            val halfHeight = size.height / 2
            val center = Offset(x = halfWidth, y = halfHeight)
            val endOffset = Offset(
                x = halfWidth,
                y = halfHeight - calculateClockHandLength(halfHeight, currentHour)
            )
            // Rotate the line around the pivot point, which is the
            // center of the screen. Rotation goes from 0 to 720 degrees
            rotate(degrees = animationAngle, pivot = center) {
                drawLine(
                    color = Color.White,
                    start = center,
                    end = endOffset,
                    strokeWidth = strokeWidth,
                )
            }

            hours.forEach { hour ->
                if(!dotsVisibility[hour]) return@forEach
                val degree = hour * 30f
                rotate(degree) {
                    val start = Offset(x = halfWidth, y = 0f)
                    val end = Offset(x = halfWidth, strokeWidth)
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
