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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.guilhermemagro.myplayground.ui.components.circularnavbar.model.Tab
import com.guilhermemagro.myplayground.ui.theme.MyPlaygroundTheme

// Stopped at https://youtu.be/4zyuGXTyZ80?si=gZZV3FjtVlp2Zoyt&t=251
@Composable
fun CustomTabBar(
    activeTab: Tab,
    modifier: Modifier = Modifier,
) {
    val iconOffsetUnit = 10

    val backgroundTopColor = MaterialTheme.colors.surface.copy(alpha = 0.8f)
    val backgroundBottomColor = MaterialTheme.colors.surface.copy(alpha = 0.5f)
    val borderStrokeColor = MaterialTheme.colors.onSurface

    fun getIconOffset(tab: Tab): Dp {
        val totalIndices = Tab.values().size
        val currentIndex = tab.ordinal
        val progress = currentIndex / totalIndices.toFloat()
        return if (progress < 0.5) {
            (currentIndex * -iconOffsetUnit).dp
        } else {
            ((totalIndices - currentIndex - 1) * -iconOffsetUnit).dp
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
    ) {
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            drawPath(
                path = Path().apply {
                    moveTo(0f, 5f.dp.toPx())
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
            drawPath(
                path = Path().apply {
                    moveTo(0f, 5f.dp.toPx())
                    relativeQuadraticBezierTo(
                        dx1 = size.width / 2,
                        dy1 = (iconOffsetUnit * -4f).dp.toPx(),
                        dx2 = size.width,
                        dy2 = 0f
                    )
                },
                color = borderStrokeColor,
                style = Stroke(),
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