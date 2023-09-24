package com.guilhermemagro.myplayground.ui.components.circularnavbar.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.theme.MyPlaygroundTheme

// Stopped at https://youtu.be/4zyuGXTyZ80?si=tRWg1sZ1VkuFFhxt&t=480
@Composable
fun CustomTabBar(
    activeTab: Tab,
    modifier: Modifier = Modifier,
) {
    val iconOffsetUnit = 10

    val backgroundTopColor = MaterialTheme.colors.surface.copy(alpha = 0.8f)
    val backgroundBottomColor = MaterialTheme.colors.surface.copy(alpha = 0.5f)
    val backgroundSurfaceColor = MaterialTheme.colors.surface
    val onSurfaceColor = MaterialTheme.colors.onSurface

    // TODO: Recalculate icon offset using math equations
    fun getIconOffset(tab: Tab): Dp {
        return when (tab.ordinal) {
            0, 4 -> 0.dp
            1, 3 -> (-10).dp
            2 -> (-15).dp
            else -> (-0).dp
        }
    }
//    val totalIndices = Tab.values().size
//        val iconPosition = currentIndex / totalIndices.toFloat()
//        return if (iconPosition < 0.5) {
//            (currentIndex * -iconOffsetUnit).dp
//        } else {
//            ((totalIndices - currentIndex - 1) * -iconOffsetUnit).dp
//        }

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
            drawRoundRect(
                color = onSurfaceColor,
                topLeft = Offset(
                    x = size.width / 2 - 45.dp.toPx() / 2,
                    y = (iconOffsetUnit * 2f).dp.toPx() + 2.dp.toPx()
                ),
                size = Size(width = 45.dp.toPx(), height = 4.dp.toPx()),
                cornerRadius = CornerRadius(2.dp.toPx()),
            )
            // Bottom surface
            val circleWidth = size.width * 5
            val circleRadius = circleWidth / 2
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
                        .offset(y = getIconOffset(it)),
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
        CustomTabBar(activeTab = Tab.LIBRARY)
    }
}