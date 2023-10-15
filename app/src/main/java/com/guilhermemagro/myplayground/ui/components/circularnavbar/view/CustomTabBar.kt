package com.guilhermemagro.myplayground.ui.components.circularnavbar.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.theme.MyPlaygroundTheme
import java.lang.Math.toDegrees
import kotlin.math.atan

// Stopped at https://youtu.be/4zyuGXTyZ80?si=GdySkdOz-qMM4wUg&t=654

// Probably will have to use `derivedStateOf()` to show/hide the CustomTabBar
// https://developer.android.com/jetpack/compose/side-effects#derivedstateof
@Composable
fun CustomTabBar(
    activeTab: Tab,
    modifier: Modifier = Modifier,
    onTabClicked: (Tab) -> Unit = {},
) {
    val iconOffsetUnit = 10

    val backgroundTopColor = MaterialTheme.colors.surface.copy(alpha = 0.8f)
    val backgroundBottomColor = MaterialTheme.colors.surface.copy(alpha = 0.5f)
    val backgroundSurfaceColor = MaterialTheme.colors.surface
    val onSurfaceColor = MaterialTheme.colors.onSurface

    var rotation by remember { mutableStateOf(0f) }
    val currentRotation: Float by animateFloatAsState(
        targetValue = rotation,
        label = "currentRotation"
    )

    // TODO: Recalculate icon offset using math equations
    fun getIconOffset(tab: Tab): Dp {
        return when (tab.ordinal) {
            0, 4 -> 0.dp
            1, 3 -> (-10).dp
            2 -> (-15).dp
            else -> (-0).dp
        }
    }

    fun calculateRotation(circleRadius: Float, viewWidth: Float): Float {
        val tabWidth = viewWidth / Tab.values().size
        // Calculating rotating from middle
        val firstTabPositionX = -(viewWidth - tabWidth) / 2
        val x = firstTabPositionX + tabWidth * activeTab.ordinal
        val tan2 = circleRadius.toDouble() / x.toDouble()
        val radians2 = atan(tan2)
        val degrees2 = toDegrees(radians2)

        return if (degrees2 < 0) {
            -(degrees2 + 90).toFloat()
        } else {
            -(degrees2 - 90).toFloat()
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 20.dp),
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            // Top surface
            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    relativeQuadraticBezierTo(
                        dx1 = size.width / 2,
                        dy1 = (iconOffsetUnit * -4f).dp.toPx(),
                        dx2 = size.width,
                        dy2 = 0f
                    )
                    lineTo(size.width, size.height)
                    lineTo(0f, size.height)
                    close()
                },
                brush = Brush.verticalGradient(
                    colors = listOf(
                        backgroundTopColor,
                        backgroundBottomColor
                    )
                ),
            )

            // Border line
            drawPath(
                path = Path().apply {
                    moveTo(0f, 0f)
                    relativeQuadraticBezierTo(
                        dx1 = size.width / 2,
                        dy1 = (iconOffsetUnit * -4f).dp.toPx(),
                        dx2 = size.width,
                        dy2 = 0f
                    )
                },
                color = onSurfaceColor,
                style = Stroke(),
            )

            // Tab marker
            val circleWidth = size.width * 5
            val circleRadius = circleWidth / 2
            rotation = calculateRotation(circleRadius = circleRadius, viewWidth = size.width)

            rotate(
                degrees = currentRotation,
                pivot = Offset(size.width / 2, size.height + circleRadius - 25.dp.toPx())
            ) {
                drawRoundRect(
                    color = onSurfaceColor,
                    topLeft = Offset(
                        x = size.width / 2 - 45.dp.toPx() / 2,
                        y = (iconOffsetUnit * 2f).dp.toPx() + 2.dp.toPx()
                    ),
                    size = Size(width = 45.dp.toPx(), height = 4.dp.toPx()),
                    cornerRadius = CornerRadius(2.dp.toPx()),
                )
            }

            // Bottom surface
            drawCircle(
                color = backgroundSurfaceColor,
                radius = circleRadius,
                center = Offset(size.width / 2, size.height + circleRadius - 25.dp.toPx())
            )
        }

        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            Tab.values().forEach {
                Icon(
                    imageVector = it.icon,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface,
                    modifier = Modifier
                        .size(30.dp)
                        .offset(y = getIconOffset(it))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                onTabClicked(it)
                            }
                        ),
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun CustomTabBarPreview() {
    MyPlaygroundTheme {
        CustomTabBar(activeTab = Tab.FOUR_SEARCH)
    }
}